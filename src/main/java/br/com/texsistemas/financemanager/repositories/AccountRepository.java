package br.com.texsistemas.financemanager.repositories;

import br.com.texsistemas.financemanager.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository <Account, UUID> {
}
