package br.com.texsistemas.financemanager.controller;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Transaction;
import br.com.texsistemas.financemanager.dto.ErrorMessage;
import br.com.texsistemas.financemanager.dto.TransactionDTO;
import br.com.texsistemas.financemanager.domain.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public record TransactionRequestDTO(
            UUID userId,
            UUID accountId,
            String categoryName,
            String description,
            BigDecimal value,
            LocalDateTime date,
            String type,
            String status,
            BigDecimal interestOrDiscount
    ) {
        public Transaction toTransaction() {
            Transaction transaction = new Transaction();
            transaction.setDescription(description());
            transaction.setValue(value());
            transaction.setDate(date());
            transaction.setType(type());
            transaction.setStatus(status());
            transaction.setInterestOrDiscount(interestOrDiscount());
            return transaction;
        }
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable UUID id) {
        Optional<TransactionDTO> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            TransactionDTO savedTransaction = transactionService.save(
                    transactionRequestDTO.toTransaction(),
                    transactionRequestDTO.userId(),
                    transactionRequestDTO.accountId(),
                    transactionRequestDTO.categoryName()
            );
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(savedTransaction.id())
                    .toUri();
            return ResponseEntity.created(uri).body(savedTransaction);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody String newStatus) {
        try {
            TransactionDTO updatedTransaction = transactionService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updatedTransaction);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            transactionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}