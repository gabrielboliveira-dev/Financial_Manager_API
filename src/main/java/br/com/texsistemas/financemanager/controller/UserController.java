package br.com.texsistemas.financemanager.controller;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.dto.UserDTO;
import br.com.texsistemas.financemanager.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        Optional<UserDTO> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam String email) {
        Optional<UserDTO> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody User user) {
        try {
            UserDTO savedUser = userService.save(user);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(savedUser);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or a more specific error DTO
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @Valid @RequestBody User user) {
        try {
            UserDTO updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or a more specific error DTO
        } catch (RuntimeException e) { // Catch any other potential exceptions
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Or a more specific error DTO
        }
    }
}