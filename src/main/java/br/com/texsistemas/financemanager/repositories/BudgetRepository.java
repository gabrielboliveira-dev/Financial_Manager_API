package br.com.texsistemas.financemanager.repositories;

import br.com.texsistemas.financemanager.domain.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository <Budget, UUID> {
}
