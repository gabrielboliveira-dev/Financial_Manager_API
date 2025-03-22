package br.com.texsistemas.financemanager.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity // Indica que esta classe é uma entidade JPA.
@Table(name = "transaction", schema = "public") // Especifica o nome da tabela ("transaction") e o schema ("public").
@Getter // Gera os getters para os atributos.
@Setter // Gera os setters para os atributos.
@NoArgsConstructor // Gera um construtor sem argumentos.
@AllArgsConstructor // Gera um construtor com argumentos para todos os atributos.
@Builder // Implementa o padrão Builder.
public class Transaction {

    @Id // Indica que este atributo é a chave primária.
    @GeneratedValue(strategy = GenerationType.AUTO) // Especifica a estratégia de geração da chave primária.
    private UUID id; // Identificador único da transação.

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade Account (muitas transações podem pertencer a uma conta).
    @JoinColumn(name = "account_id", nullable = false) // Especifica a coluna da chave estrangeira ("account_id") na tabela de transações, que referencia a tabela de contas. O atributo nullable = false indica que uma transação deve sempre estar associada a uma conta.
    private Account account; // A conta à qual esta transação pertence.

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade Category (muitas transações podem pertencer a uma categoria).
    @JoinColumn(name = "category_id", nullable = false) // Especifica a coluna da chave estrangeira ("category_id") na tabela de transações, que referencia a tabela de categorias. O atributo nullable = false indica que uma transação deve sempre ter uma categoria.
    private Category category; // A categoria desta transação.

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade User (muitas transações podem ser criadas por um usuário).
    @JoinColumn(name = "user_id", nullable = false) // Especifica a coluna da chave estrangeira ("user_id") na tabela de transações, que referencia a tabela de usuários. O atributo nullable = false indica que uma transação deve sempre estar associada a um usuário.
    private User user; // O usuário que criou esta transação.

    @Column(nullable = false, length = 255) // Especifica a coluna "description" no banco de dados: não pode ser nula e tem um tamanho máximo de 255 caracteres.
    private String description; // Uma descrição da transação.

    @Column(nullable = false, precision = 12, scale = 2) // Especifica a coluna "value" no banco de dados: não pode ser nula, tem uma precisão de 12 dígitos e 2 casas decimais (ideal para valores monetários).
    private BigDecimal value; // O valor da transação.

    @Column(nullable = false, precision = 12, scale = 2) // Especifica a coluna "interest_or_discount" no banco de dados: pode ser nula (caso não haja juros ou desconto), tem uma precisão de 12 dígitos e 2 casas decimais.
    private BigDecimal interestOrDiscount; // O valor de juros ou desconto aplicado à transação.

    @Column(nullable = false) // Especifica a coluna "date" no banco de dados: não pode ser nula.
    private LocalDateTime date; // A data em que a transação ocorreu.

    @Column(nullable = false, length = 50) // Especifica a coluna "type" no banco de dados: não pode ser nula e tem um tamanho máximo de 50 caracteres.
    private String type; // O tipo da transação (por exemplo, "RECEITA" ou "DESPESA").

    @Column(nullable = false, length = 50) // Especifica a coluna "status" no banco de dados: não pode ser nula e tem um tamanho máximo de 50 caracteres.
    private String status; // O status da transação (por exemplo, "PENDENTE", "PAGO", "VENCIDO", "ABERTO").
}