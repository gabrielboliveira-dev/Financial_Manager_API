package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Transactional(readOnly = true)
    List<Transaction> findByAccountId(UUID accountId);

    @Transactional(readOnly = true)
    List<Transaction> findByCategoryId(UUID categoryId);

    @Transactional(readOnly = true)
    List<Transaction> findByType(String type);
}
