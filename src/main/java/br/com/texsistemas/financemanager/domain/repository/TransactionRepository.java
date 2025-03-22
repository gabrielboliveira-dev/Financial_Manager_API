package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    //Busca todas as transações.
    @Transactional(readOnly = true)
    List<Transaction> findAll();

    //Busca todas as transações associadas a uma determinada conta.
    @Transactional(readOnly = true)
    List<Transaction> findByAccountId(UUID accountId);

    //Busca todas as transações pertencentes a uma determinada categoria
    @Transactional(readOnly = true)
    List<Transaction> findByCategoryId(UUID categoryId);

    //busca todas as transações de um determinado tipo (por exemplo, "RECEITA" ou "DESPESA").
    @Transactional(readOnly = true)
    List<Transaction> findByType(String type);

    //Busca todas as transações associadas a um determinado utilizador.
    @Transactional(readOnly = true)
    List<Transaction> findByAccount_User_Id(UUID userId);
}