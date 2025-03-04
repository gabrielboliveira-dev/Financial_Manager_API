package br.com.texsistemas.financemanager.repositories;

import br.com.texsistemas.financemanager.domain.budget.Budget;
import br.com.texsistemas.financemanager.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository <Category, UUID> {
}
