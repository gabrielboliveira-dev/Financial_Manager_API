package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    List<Budget> findByUser_Id(UUID userId);

    List<Budget> findByUser_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(UUID userId, LocalDate startDate, LocalDate endDate);
}