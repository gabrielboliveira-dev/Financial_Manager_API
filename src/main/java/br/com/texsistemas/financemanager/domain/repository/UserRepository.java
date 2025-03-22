package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    //A operação será executada numa transação e que apenas operações de leitura serão realizadas, otimizando o desempenho.
    //Busca um utilizador no banco de dados pelo seu endereço de endereço eletrónico.
    //Retorna um Optional<User> para lidar com o caso em que nenhum utilizador com o endereço eletrónico fornecido seja encontrado.
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    //Busca um utilizador pelo seu endereço de endereço eletrónico, mas apenas se o campo 'ativo' for verdadeiro.
    @Transactional(readOnly = true)
    Optional<User> findByEmailAndAtivoTrue(String email);

    //Busca um utilizador pelo seu ‘ID’, mas apenas se o campo 'ativo' for verdadeiro.
    @Transactional(readOnly = true)
    Optional<User> findByIdAndAtivoTrue(UUID id);

    //Busca um utilizador pelo seu CPF.
    @Transactional(readOnly = true)
    Optional<User> findByCpf(String cpf);

    //busca todos os utilizadores que possuem o campo 'ativo' como verdadeiro e retorna uma lista deles.
    @Transactional(readOnly = true)
    List<User> findByAtivoTrue();
}