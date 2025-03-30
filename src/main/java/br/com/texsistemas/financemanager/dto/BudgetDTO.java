package br.com.texsistemas.financemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BudgetDTO (
        UUID id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal amount,
        UUID categoryId
) {
}