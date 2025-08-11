# Financial Manager API

### Descrição do Projeto

Este projeto é uma API RESTful para um sistema de gerenciamento financeiro pessoal. Desenvolvido com **Spring Boot 3**, ele segue os princípios de **Arquitetura Limpa**, **SOLID** e **Clean Code** para garantir uma base de código robusta, testável e de fácil manutenção. A aplicação gerencia usuários, contas, orçamentos, categorias e transações.

Como um diferencial, a interface principal para interação com o usuário final é um **bot do Telegram**, que permite a realização de tarefas rápidas e o acesso a resumos financeiros sem a necessidade de um front-end web ou mobile tradicional.

-----

### Funcionalidades

  * **Gestão de Usuários**: CRUD (Create, Read, Update, Deactivate) para usuários com validação de CPF e e-mail.
  * **Gestão de Contas**: Permite criar, listar e gerenciar múltiplas contas financeiras por usuário.
  * **Gestão de Transações**: Registro detalhado de receitas e despesas, com associação a contas e categorias.
  * **Gestão de Categorias**: Criação e gestão de categorias personalizadas por usuário.
  * **Gestão de Orçamentos**: Permite definir orçamentos por categoria para acompanhar e controlar gastos.
  * **Autenticação JWT**: Acesso seguro aos endpoints da API através de tokens JWT (JSON Web Token).
  * **Interface via Bot do Telegram**: Interação principal com o usuário através de um bot de chat para comandos de gerenciamento e acesso a resumos.

-----

### Arquitetura e Princípios de Design

O projeto é construído com base nos princípios da **Arquitetura Limpa**, organizando o código em camadas concêntricas que garantem a separação de responsabilidades e a independência da infraestrutura.

  - **Camada Core/Domínio**: Contém as entidades (`User`, `Transaction`, `Account`, etc.), as interfaces de repositório e a lógica de negócio mais importante.
  - **Camada de Infraestrutura**: Inclui as implementações de repositório (com Spring Data JPA e Hibernate), a conexão com o banco de dados PostgreSQL, as migrations do Flyway e a integração com APIs externas (como a API do Telegram).
  - **Camada de Apresentação**: É a interface com o mundo exterior. Inclui os `Controllers` da API REST e a lógica do bot do Telegram.

-----

### Tecnologias Utilizadas

  * **Linguagem**: Java 17
  * **Framework**: Spring Boot 3
  * **Banco de Dados**: PostgreSQL, Flyway (migrations)
  * **Persistência**: Spring Data JPA, Hibernate
  * **Autenticação**: Spring Security, Java JWT (jjwt)
  * **Ferramentas de Build**: Maven
  * **Bibliotecas**: Lombok, MapStruct
  * **Testes**: JUnit 5, Mockito, MockMvc, Testcontainers
  * **Interface**: API do Telegram (para o bot)
  * **Documentação**: Springdoc OpenAPI (Swagger UI)

-----

### Como Começar

Siga os passos para configurar e executar a aplicação localmente:

**Pré-requisitos:**

  * Java 17 JDK
  * Maven
  * Docker (para o PostgreSQL e Testcontainers)
  * Uma conta no Telegram (para testar o bot)

**Configuração do Ambiente:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/seu-projeto.git
    cd seu-projeto
    ```
2.  **Configurar o Banco de Dados:**
      * Inicie um contêiner Docker para o PostgreSQL.
      * Crie um banco de dados e um usuário.
      * Configure as credenciais no arquivo `src/main/resources/application.properties`.
    <!-- end list -->
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/financemanager
    spring.datasource.username=financemanager_user
    spring.datasource.password=sua_senha
    ```
3.  **Configurar o Bot do Telegram:**
      * Crie um bot usando o **BotFather** no Telegram e obtenha o token de acesso.
      * Adicione o token ao `application.properties`.
    <!-- end list -->
    ```properties
    telegram.bot.token=SEU_TOKEN_DO_BOT
    ```

**Executando a Aplicação:**
Execute a aplicação com o Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080` e a documentação do Swagger UI poderá ser acessada em `http://localhost:8080/swagger-ui.html`. O bot do Telegram estará online para receber comandos.
