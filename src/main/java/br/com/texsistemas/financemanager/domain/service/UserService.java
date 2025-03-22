package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import br.com.texsistemas.financemanager.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    //Buscar utilizadores ativos, quando eu encontro, converto em DTO para criar um UserDTO, coleto os UserDTOs numa lista e retorno.
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findByAtivoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //Busco um utilizador ativo pelo seu ‘ID’, caso eu encontre, converto em UserDTO.
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .map(this::convertToDTO);
    }

    //Busco um utilizador ativo pelo seu endereço eletrónico, caso eu encontre, converto em UserDTO.
    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmailAndAtivoTrue(email)
                .map(this::convertToDTO);
    }

    // Verifica se já existe um utilizador com o mesmo endereço eletrónico ou CPF.
    // Se existir, lança uma exceção de negócio.
    // Defino data de criação, se está ativo, criptógrafo a senha para salvar o utilizador no UserDTO.
    @Transactional
    public UserDTO save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail.");
        }
        if (userRepository.findByCpf(user.getCpf()).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com este CPF.");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setAtivo(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    //Busco o utilizador pelo 'ID', faça analises do endereço eletrónico e CPF, se houver alteração e não for encontrado, lanço exceções.
    @Transactional
    public UserDTO update(UUID id, User updatedUser) {
        Optional<User> currentUserOptional = userRepository.findByIdAndAtivoTrue(id);

        if (currentUserOptional.isEmpty()) {
            throw new BusinessException("Usuário não encontrado ou inativo.");
        }

        User currentUser = currentUserOptional.get();

        if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
            throw new BusinessException("O e-mail não pode ser alterado.");
        }

        if (!currentUser.getCpf().equals(updatedUser.getCpf())) {
            throw new BusinessException("O CPF não pode ser alterado.");
        }

        // Copia as propriedades do updatedUser para o currentUser, ignorando os campos que não devem ser alterados (‘id’, email, cpf, createdAt, ativo).
        // Verifica se uma nova senha foi fornecida. Se sim, criptógrafa a nova senha.
        // Salva as alterações no usuário. E Converte o usuário atualizado para UserDTO.
        BeanUtils.copyProperties(updatedUser, currentUser, "id", "email", "cpf", "createdAt", "ativo");
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        User savedUser = userRepository.save(currentUser);
        return convertToDTO(savedUser);
    }

    // Busca o usuário ativo pelo ‘ID’ se o usuário foi encontrado, pego ele.
    // Defino o status do usuário como inativo e salvo as alterações no banco de dados.
    // Se não encontro, lanço uma exceção.
    @Transactional
    public void delete(UUID id) {
        Optional<User> userOptional = userRepository.findByIdAndAtivoTrue(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAtivo(false);
            userRepository.save(user);
        } else {
            throw new BusinessException("Usuário não encontrado ou já inativo.");
        }
    }

    //Converter uma entidade User para um UserDTO, expondo apenas os campos necessários
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCpf());
    }
}