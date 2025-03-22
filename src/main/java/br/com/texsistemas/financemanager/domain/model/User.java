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

@Entity // Indica que esta classe é uma entidade JPA, ou seja, representa uma tabela no banco de dados.
@Table(name = "users", schema = "public") // Especifica o nome da tabela no banco de dados ("users") e o schema ("public").
@Getter // Anotação do Lombok que gera automaticamente todos os métodos getters para os atributos da classe.
@Setter // Anotação do Lombok que gera automaticamente todos os métodos setters para os atributos da classe.
@NoArgsConstructor // Anotação do Lombok que gera automaticamente um construtor sem argumentos.
@AllArgsConstructor // Anotação do Lombok que gera automaticamente um construtor com argumentos para todos os atributos da classe.
@Builder // Anotação do Lombok que implementa o padrão de projeto Builder para facilitar a criação de instâncias da classe.
public class User {

    @Id // Indica que este atributo é a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.AUTO) // Especifica a estratégia de geração da chave primária. GenerationType.AUTO permite que o JPA escolha a melhor estratégia para o banco de dados utilizado.
    private UUID id; // Identificador único do usuário, utilizando o tipo UUID.

    @NotBlank // Anotação do Bean Validation que garante que o campo não seja nulo nem vazio.
    @Size(max = 100) // Anotação do Bean Validation que define o tamanho máximo do campo para 100 caracteres.
    @Column(nullable = false, length = 100) // Especifica a coluna correspondente no banco de dados: não pode ser nula e tem um tamanho máximo de 100 caracteres.
    private String name; // Nome completo do usuário.

    @NotBlank // Anotação do Bean Validation que garante que o campo não seja nulo nem vazio.
    @Email // Anotação do Bean Validation que garante que o valor do campo seja um endereço de e-mail válido.
    @Size(max = 150) // Anotação do Bean Validation que define o tamanho máximo do campo para 150 caracteres.
    @Column(nullable = false, unique = true, length = 150) // Especifica a coluna correspondente no banco de dados: não pode ser nula, deve ser única (não pode haver dois usuários com o mesmo e-mail) e tem um tamanho máximo de 150 caracteres.
    private String email; // Endereço de e-mail do usuário.

    @NotBlank // Anotação do Bean Validation que garante que o campo não seja nulo nem vazio.
    @CPF // Anotação do Hibernate Validator que garante que o valor do campo seja um CPF válido.
    @Size(max = 11) // Anotação do Bean Validation que define o tamanho máximo do campo para 11 caracteres (o tamanho de um CPF).
    @Column(nullable = false, unique = true, length = 11) // Especifica a coluna correspondente no banco de dados: não pode ser nula, deve ser única e tem um tamanho máximo de 11 caracteres.
    private String cpf; // Cadastro de Pessoa Física do usuário.

    @NotBlank // Anotação do Bean Validation que garante que o campo não seja nulo nem vazio.
    @Column(nullable = false) // Especifica a coluna correspondente no banco de dados: não pode ser nula.
    private String password; // Senha do usuário (será armazenada de forma criptografada).

    @Column(nullable = false) // Especifica a coluna correspondente no banco de dados: não pode ser nula.
    private LocalDateTime createdAt; // Data e hora em que o usuário foi criado.

    @Column(nullable = false) // Especifica a coluna correspondente no banco de dados: não pode ser nula.
    private Boolean ativo; // Indica se o usuário está ativo (true) ou inativo (false).

    // Método para definir a senha do usuário, criptografando-a antes de salvar.
    public void setPassword(String rawPassword) {
        this.password = new BCryptPasswordEncoder().encode(rawPassword);
    }
}