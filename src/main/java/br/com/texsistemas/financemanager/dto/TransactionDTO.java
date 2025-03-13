package br.com.texsistemas.financemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Date;

public record TransactionDTO(
        UUID id,
        UUID accountId,
        UUID categoryId,
        String description,
        Float value,
        Date date,
        String type,
        String status
) {
    public TransactionDTO(UUID id, UUID id1, UUID id2, String description, BigDecimal value, LocalDateTime date, String type, String status) {
    }
}
