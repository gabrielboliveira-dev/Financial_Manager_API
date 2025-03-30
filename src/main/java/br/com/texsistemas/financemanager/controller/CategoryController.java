package br.com.texsistemas.financemanager.controller;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Category;
import br.com.texsistemas.financemanager.dto.CategoryDTO;
import br.com.texsistemas.financemanager.dto.ErrorMessage;
import br.com.texsistemas.financemanager.domain.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createCategory(@PathVariable UUID userId, @Valid @RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category, userId);
            CategoryDTO categoryDTO = new CategoryDTO(createdCategory.getId(), createdCategory.getName(), createdCategory.getDescription());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(categoryDTO.id())
                    .toUri();
            return ResponseEntity.created(uri).body(categoryDTO);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);
        if (categoryOptional.isPresent()) {
            CategoryDTO categoryDTO = new CategoryDTO(categoryOptional.get().getId(), categoryOptional.get().getName(), categoryOptional.get().getDescription());
            return ResponseEntity.ok(categoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByUser(@PathVariable UUID userId) {
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID id, @Valid @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            CategoryDTO categoryDTO = new CategoryDTO(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getDescription());
            return ResponseEntity.ok(categoryDTO);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}