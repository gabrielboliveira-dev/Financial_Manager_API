package br.com.texsistemas.financemanager.dto;

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
) {}
