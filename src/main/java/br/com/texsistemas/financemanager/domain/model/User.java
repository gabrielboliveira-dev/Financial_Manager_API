package br.com.texsistemas.financemanager.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    //Geração da chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Nome completo do utilizador
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    // Endereço de endereço eletrónico do utilizador.
    @NotBlank
    @Email
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // Cadastro de Pessoa Física do utilizador.
    @NotBlank
    @CPF
    @Size(max = 11)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    // Senha do utilizador.
    @NotBlank
    @Column(nullable = false)
    private String password;

    // Data e hora em que o utilizador foi criado.
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Indica se o usuário está ativo (true) ou inativo (false).
    @Column(nullable = false)
    private Boolean ativo;

    // Criptografia Hash antes de salvar.
    // Isso para garantir que a senha real do usuário nunca seja armazenada em texto plano no banco de dados
    public void setPassword(String rawPassword) {
        this.password = new BCryptPasswordEncoder().encode(rawPassword);
    }
}