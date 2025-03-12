package br.com.texsistemas.financemanager.dto;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String email
) {}

