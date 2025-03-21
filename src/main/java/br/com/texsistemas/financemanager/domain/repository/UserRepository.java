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

    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findByEmailAndAtivoTrue(String email);

    @Transactional(readOnly = true)
    Optional<User> findByIdAndAtivoTrue(UUID id);

    @Transactional(readOnly = true)
    Optional<User> findByCpf(String cpf);

    @Transactional(readOnly = true)
    List<User> findByAtivoTrue();
}