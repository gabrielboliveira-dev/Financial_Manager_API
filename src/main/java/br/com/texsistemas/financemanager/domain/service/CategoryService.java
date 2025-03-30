package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.exception.BusinessException;
import br.com.texsistemas.financemanager.domain.model.Category;
import br.com.texsistemas.financemanager.domain.model.User;
import br.com.texsistemas.financemanager.domain.repository.CategoryRepository;
import br.com.texsistemas.financemanager.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Category createCategory(Category category, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado.", "user.not-found"));

        if (categoryRepository.existsByUser_IdAndName(userId, category.getName())) {
            throw new BusinessException("Já existe uma categoria com este nome para este usuário.", "category.name-exists");
        }

        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getCategoriesByUser(UUID userId) {
        return categoryRepository.findByUser_Id(userId);
    }

    public Category updateCategory(UUID id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada.", "category.not-found"));

        if (!existingCategory.getName().equals(categoryDetails.getName()) &&
                categoryRepository.existsByUser_IdAndName(existingCategory.getUser().getId(), categoryDetails.getName())) {
            throw new BusinessException("Já existe uma categoria com este nome para este usuário.", "category.name-exists");
        }

        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Categoria não encontrada.", "category.not-found");
        }
        categoryRepository.deleteById(id);
    }
}