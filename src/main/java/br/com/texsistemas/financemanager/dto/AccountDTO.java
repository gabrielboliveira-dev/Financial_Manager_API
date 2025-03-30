package br.com.texsistemas.financemanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountDTO {

    private UUID id;
    private String accountNumber;
    private String agencyNumber;
    private String accountName;
    private String accountType;
    private BigDecimal balance;
    private LocalDateTime creationDate;
    private UUID userId;
}