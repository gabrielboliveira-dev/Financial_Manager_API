package br.com.texsistemas.financemanager.dto;

import java.util.UUID;

public record CategoryDTO (
        UUID id,
        String name,
        String description
) {
}