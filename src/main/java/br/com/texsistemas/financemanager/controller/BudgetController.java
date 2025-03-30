package br.com.texsistemas.financemanager.controller;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Budget;
import br.com.texsistemas.financemanager.dto.BudgetDTO;
import br.com.texsistemas.financemanager.dto.ErrorMessage;
import br.com.texsistemas.financemanager.domain.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createBudget(@PathVariable UUID userId, @RequestParam(required = false) UUID categoryId, @Valid @RequestBody Budget budget) {
        try {
            Budget createdBudget = budgetService.createBudget(budget, userId, categoryId);
            BudgetDTO budgetDTO = new BudgetDTO(createdBudget.getId(), createdBudget.getName(), createdBudget.getDescription(),
                    createdBudget.getStartDate(), createdBudget.getEndDate(), createdBudget.getAmount(),
                    createdBudget.getCategory() != null ? createdBudget.getCategory().getId() : null);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(budgetDTO.id())
                    .toUri();
            return ResponseEntity.created(uri).body(budgetDTO);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable UUID id) {
        Optional<Budget> budgetOptional = budgetService.getBudgetById(id);
        if (budgetOptional.isPresent()) {
            BudgetDTO budgetDTO = new BudgetDTO(budgetOptional.get().getId(), budgetOptional.get().getName(), budgetOptional.get().getDescription(),
                    budgetOptional.get().getStartDate(), budgetOptional.get().getEndDate(), budgetOptional.get().getAmount(),
                    budgetOptional.get().getCategory() != null ? budgetOptional.get().getCategory().getId() : null);
            return ResponseEntity.ok(budgetDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByUser(@PathVariable UUID userId) {
        List<Budget> budgets = budgetService.getBudgetsByUser(userId);
        List<BudgetDTO> budgetDTOs = budgets.stream()
                .map(budget -> new BudgetDTO(budget.getId(), budget.getName(), budget.getDescription(),
                        budget.getStartDate(), budget.getEndDate(), budget.getAmount(),
                        budget.getCategory() != null ? budget.getCategory().getId() : null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(budgetDTOs);
    }

    @GetMapping("/users/{userId}/range")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByUserAndDateRange(@PathVariable UUID userId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Budget> budgets = budgetService.getBudgetsByUserAndDateRange(userId, startDate, endDate);
        List<BudgetDTO> budgetDTOs = budgets.stream()
                .map(budget -> new BudgetDTO(budget.getId(), budget.getName(), budget.getDescription(),
                        budget.getStartDate(), budget.getEndDate(), budget.getAmount(),
                        budget.getCategory() != null ? budget.getCategory().getId() : null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(budgetDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable UUID id, @RequestParam(required = false) UUID categoryId, @Valid @RequestBody Budget budgetDetails) {
        try {
            Budget updatedBudget = budgetService.updateBudget(id, budgetDetails, categoryId);
            BudgetDTO budgetDTO = new BudgetDTO(updatedBudget.getId(), updatedBudget.getName(), updatedBudget.getDescription(),
                    updatedBudget.getStartDate(), updatedBudget.getEndDate(), updatedBudget.getAmount(),
                    updatedBudget.getCategory() != null ? updatedBudget.getCategory().getId() : null);
            return ResponseEntity.ok(budgetDTO);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        try {
            budgetService.deleteBudget(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}