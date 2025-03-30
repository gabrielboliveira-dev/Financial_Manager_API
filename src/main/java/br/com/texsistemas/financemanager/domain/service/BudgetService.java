package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Budget;
import br.com.texsistemas.financemanager.domain.model.Category;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.BudgetRepository;
import br.com.texsistemas.financemanager.domain.repository.CategoryRepository;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Budget createBudget(Budget budget, UUID userId, UUID categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado.", "user.not-found"));
        budget.setUser(user);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessException("Categoria não encontrada.", "category.not-found"));
            budget.setCategory(category);
        }

        return budgetRepository.save(budget);
    }

    public Optional<Budget> getBudgetById(UUID id) {
        return budgetRepository.findById(id);
    }

    public List<Budget> getBudgetsByUser(UUID userId) {
        return budgetRepository.findByUser_Id(userId);
    }

    public List<Budget> getBudgetsByUserAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return budgetRepository.findByUser_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(userId, startDate, endDate);
    }

    public Budget updateBudget(UUID id, Budget budgetDetails, UUID categoryId) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Orçamento não encontrado.", "budget.not-found"));

        existingBudget.setName(budgetDetails.getName());
        existingBudget.setDescription(budgetDetails.getDescription());
        existingBudget.setStartDate(budgetDetails.getStartDate());
        existingBudget.setEndDate(budgetDetails.getEndDate());
        existingBudget.setAmount(budgetDetails.getAmount());

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessException("Categoria não encontrada.", "category.not-found"));
            existingBudget.setCategory(category);
        } else {
            existingBudget.setCategory(null);
        }

        return budgetRepository.save(existingBudget);
    }

    public void deleteBudget(UUID id) {
        if (!budgetRepository.existsById(id)) {
            throw new BusinessException("Orçamento não encontrado.", "budget.not-found");
        }
        budgetRepository.deleteById(id);
    }
}