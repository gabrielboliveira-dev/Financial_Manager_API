package br.com.texsistemas.financemanager.dto;

import java.util.UUID;

public record AccountDTO(
        UUID id,
        String name,
        String type,
        Float openingBalance,
        String coin
) {}
