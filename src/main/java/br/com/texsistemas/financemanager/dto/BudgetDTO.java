package br.com.texsistemas.financemanager.dto;

import java.util.UUID;
import java.util.Date;

public record BudgetDTO(
        UUID id,
        UUID userId,
        UUID categoryId,
        Float limit,
        Date dateStart,
        Date dateEnd
) {}
