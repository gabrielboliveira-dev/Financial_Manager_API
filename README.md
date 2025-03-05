# Gerenciador de Finanças

## Descrição

O **Gerenciador de Finanças** é um sistema desenvolvido em **Java** utilizando **Spring Boot** para auxiliar no controle financeiro pessoal. O sistema permite cadastrar usuários, contas, orçamentos, categorias de despesas e transações, proporcionando uma visão clara da saúde financeira do usuário.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **Spring Web**
- **Spring Boot DevTools**
- **Flyway Migration**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **Thymeleaf**

---

## Estrutura do Projeto

```
financemanager/
├── src/
│   ├── main/java/br/com/texsistemas/financemanager/
│   │   ├── controller/      # Controladores REST
│   │   ├── domain/
│   │   │   ├── model/       # Entidades do banco de dados
│   │   │   ├── repository/  # Interfaces JPA
│   │   │   ├── service/     # Regras de negócio e serviços
│   │   │   ├── DTO/         # Objetos de Transferência de Dados
│   │   ├── application.properties # Configuração do banco de dados
│   ├── test/java/br/com/texsistemas/financemanager/ # Testes unitários
├── pom.xml  # Configurações e dependências do Maven
└── README.md
```

---

## Configuração do Banco de Dados

O projeto utiliza **PostgreSQL**. Configure seu `application.properties` com as credenciais corretas:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financemanager
spring.datasource.username=gabrielboliveira
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Como Executar o Projeto

1. Clone este repositório:
   ```sh
   git clone https://github.com/Gabriel-Oliveiraa/financemanager.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd financemanager
   ```
3. Compile e execute o projeto com Maven:
   ```sh
   mvn spring-boot:run
   ```
4. O sistema estará disponível em:
   ```
   http://localhost:8080
   ```

---

## Endpoints Disponíveis

- `POST /usuarios` - Cadastra um novo usuário
- `GET /usuarios/{id}` - Obtém um usuário por ID
- `POST /transacoes` - Registra uma nova transação
- `GET /transacoes` - Lista todas as transações
- `POST /categorias` - Cadastra uma nova categoria
- `GET /categorias` - Lista todas as categorias

---

## Melhorias Futuras

- Implementação de autenticação e autorização
- Testes unitários e de integração
- Dashboard para visualização dos gastos
- Notificações e lembretes financeiros

---

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:

1. Fork o repositório
2. Crie um branch para sua funcionalidade:
   ```sh
   git checkout -b minha-nova-funcionalidade
   ```
3. Commit suas alterações:
   ```sh
   git commit -m "Adicionando nova funcionalidade"
   ```
4. Envie para o repositório:
   ```sh
   git push origin minha-nova-funcionalidade
   ```
5. Abra um Pull Request

---

## Autor

Projeto desenvolvido por **Gabriel Oliveira** 🚀

