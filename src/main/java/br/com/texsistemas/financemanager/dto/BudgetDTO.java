package br.com.texsistemas.financemanager.dto;

import java.util.UUID;

public record BudgetDTO(
        UUID id,
        UUID userId,
        UUID categoryId,
        Float limit,
        java.time.LocalDate dateStart,
        java.time.LocalDate dateEnd
) {}
