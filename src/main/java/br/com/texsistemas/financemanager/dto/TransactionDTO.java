package br.com.texsistemas.financemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDTO(
        UUID id,
        UUID accountId,
        UUID categoryId,
        String description,
        BigDecimal value,
        LocalDateTime date,
        String type,
        String status
) {
}