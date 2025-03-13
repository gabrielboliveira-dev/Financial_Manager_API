package br.com.texsistemas.financemanager.domain.service;

import br.com.texsistemas.financemanager.domain.model.Category;
import br.com.texsistemas.financemanager.domain.repository.CategoryRepository;
import br.com.texsistemas.financemanager.dto.CategoryDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.getType()))
                .toList();
    }

    public Optional<CategoryDTO> findById(UUID id) {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.getType()));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}
