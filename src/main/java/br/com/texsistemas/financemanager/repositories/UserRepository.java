package br.com.texsistemas.financemanager.repositories;

import br.com.texsistemas.financemanager.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>{
}
