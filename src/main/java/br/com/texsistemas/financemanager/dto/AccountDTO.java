package br.com.texsistemas.financemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountDTO (

        UUID id,
        String accountNumber,
        String agencyNumber,
        String accountName,
        String accountType,
        BigDecimal balance,
        LocalDateTime creationDate,
        UUID userId
) {
}