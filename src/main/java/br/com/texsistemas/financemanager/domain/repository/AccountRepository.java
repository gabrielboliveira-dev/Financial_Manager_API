package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Transactional(readOnly = true)
    List<Account> findByUserId(UUID userId);

    @Transactional(readOnly = true)
    List<Account> findByType(String type);
}
