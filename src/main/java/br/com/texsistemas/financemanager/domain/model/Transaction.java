package br.com.texsistemas.financemanager.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    // Identificador único da transação.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // A conta à qual esta transação pertence.
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // A categoria da transação.
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // A categoria desta transação.

    // O utilizador que fez esta transação.
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Uma descrição da transação.
    @Column(nullable = false, length = 255)
    private String description;

    // O valor da transação.
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    // O valor de juros ou desconto aplicado à transação.
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal interestOrDiscount;

    // A data em que a transação ocorreu.
    @Column(nullable = false)
    private LocalDateTime date;

    // O tipo da transação (por exemplo, pode ser uma "RECEITA" ou "DESPESA").
    @Column(nullable = false, length = 50)
    private String type;

    // O status da transação (por exemplo, "PENDENTE", "PAGO", "VENCIDO", "ABERTO").
    @Column(nullable = false, length = 50)
    private String status;
}