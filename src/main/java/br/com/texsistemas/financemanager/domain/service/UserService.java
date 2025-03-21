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

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findByAtivoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmailAndAtivoTrue(email)
                .map(this::convertToDTO);
    }

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

        BeanUtils.copyProperties(updatedUser, currentUser, "id", "email", "cpf", "createdAt", "ativo");
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        User savedUser = userRepository.save(currentUser);
        return convertToDTO(savedUser);
    }

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

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCpf());
    }
}