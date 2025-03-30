package br.com.texsistemas.financemanager.controller;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Account;
import br.com.texsistemas.financemanager.dto.AccountDTO;
import br.com.texsistemas.financemanager.dto.ErrorMessage;
import br.com.texsistemas.financemanager.domain.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createAccount(@PathVariable UUID userId, @Valid @RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account, userId);
            AccountDTO accountDTO = convertToDTO(createdAccount);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(accountDTO.id())
                    .toUri();
            return ResponseEntity.created(uri).body(accountDTO);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        Optional<Account> accountOptional = accountService.getAccountById(id);
        return accountOptional.map(account -> ResponseEntity.ok(convertToDTO(account)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByUser(@PathVariable UUID userId) {
        try {
            List<Account> accounts = accountService.getAccountsByUser(userId);
            List<AccountDTO> accountDTOs = accounts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(accountDTOs);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userId}/type")
    public ResponseEntity<List<AccountDTO>> getAccountsByType(@PathVariable UUID userId, @RequestParam String type) {
        try {
            List<Account> accounts = accountService.getAccountsByType(userId, type);
            List<AccountDTO> accountDTOs = accounts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(accountDTOs);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna apenas o status NOT_FOUND
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable UUID id, @Valid @RequestBody Account accountDetails) {
        try {
            Optional<Account> existingAccountOptional = accountService.getAccountById(id);
            if (existingAccountOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            accountDetails.setId(id);
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(convertToDTO(updatedAccount));
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorMessage> deleteAccount(@PathVariable UUID id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getErrorCode()), HttpStatus.NOT_FOUND);
        }
    }

    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAgencyNumber(),
                account.getAccountName(),
                account.getAccountType(),
                account.getBalance(),
                account.getCreationDate(),
                account.getUser().getId()
        );
    }
}