package br.com.texsistemas.financemanager.domain.repository;

import br.com.texsistemas.financemanager.domain.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    @Transactional(readOnly = true)
    List<Budget> findByUserId(UUID userId);

    @Transactional(readOnly = true)
    List<Budget> findByCategoryId(UUID categoryId);

    @Transactional(readOnly = true)
    List<Budget> findByDateStartBetween(Date startDate, Date endDate);
}
