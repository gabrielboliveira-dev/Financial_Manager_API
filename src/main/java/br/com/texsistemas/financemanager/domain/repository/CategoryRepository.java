package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Transactional(readOnly = true)
    Optional<Category> findByName(String name);

    @Transactional(readOnly = true)
    List<Category> findByType(String type);
}
