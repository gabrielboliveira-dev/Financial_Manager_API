package br.com.texsistemas.financemanager.repositories;

import br.com.texsistemas.financemanager.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository <Transaction, UUID> {
}
