package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import br.com.texsistemas.financemanager.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
