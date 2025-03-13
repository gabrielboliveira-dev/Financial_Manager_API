package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.Budget;
import br.com.texsistemas.financemanager.domain.repository.BudgetRepository;
import br.com.texsistemas.financemanager.dto.BudgetDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<BudgetDTO> findAll() {
        return budgetRepository.findAll()
                .stream()
                .map(budget -> new BudgetDTO(
                        budget.getId(),
                        budget.getUser().getId(),
                        budget.getCategory().getId(),
                        budget.getLimit(),
                        budget.getDateStart(),
                        budget.getDateEnd()
                )).toList();
    }

    public Optional<BudgetDTO> findById(UUID id) {
        return budgetRepository.findById(id)
                .map(budget -> new BudgetDTO(
                        budget.getId(),
                        budget.getUser().getId(),
                        budget.getCategory().getId(),
                        budget.getLimit(),
                        budget.getDateStart(),
                        budget.getDateEnd()
                ));
    }

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public void delete(UUID id) {
        budgetRepository.deleteById(id);
    }
}
