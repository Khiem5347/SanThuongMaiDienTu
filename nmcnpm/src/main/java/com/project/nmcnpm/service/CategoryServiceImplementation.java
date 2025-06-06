package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.CategoryRepository;
import com.project.nmcnpm.dto.CategoryDTO;
import com.project.nmcnpm.dto.CategoryResponseDTO;
import com.project.nmcnpm.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Category not found with id: " + categoryDTO.getParentId()));
            category.setParentCategory(parentCategory);
            parentCategory.addSubCategory(category);
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findByIdWithDetails(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        return new CategoryResponseDTO(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        if (categoryDTO.getCategoryName() != null && !categoryDTO.getCategoryName().isEmpty()) {
            existingCategory.setCategoryName(categoryDTO.getCategoryName());
        }
        if (categoryDTO.getParentId() != null) {
            if (existingCategory.getParentCategory() == null || !existingCategory.getParentCategory().getCategoryId().equals(categoryDTO.getParentId())) {
                Category newParentCategory = categoryRepository.findById(categoryDTO.getParentId())
                        .orElseThrow(() -> new EntityNotFoundException("New Parent Category not found with id: " + categoryDTO.getParentId()));
                if (existingCategory.getParentCategory() != null) {
                    existingCategory.getParentCategory().removeSubCategory(existingCategory);
                }
                existingCategory.setParentCategory(newParentCategory);
                newParentCategory.addSubCategory(existingCategory);
            }
        } else {
            if (existingCategory.getParentCategory() != null) {
                existingCategory.getParentCategory().removeSubCategory(existingCategory);
                existingCategory.setParentCategory(null);
            }
        }
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        Category categoryToDelete = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        if (categoryToDelete.getParentCategory() != null) {
            categoryToDelete.getParentCategory().removeSubCategory(categoryToDelete);
        }
        if (categoryToDelete.getSubCategories() != null && !categoryToDelete.getSubCategories().isEmpty()) {
            for (Category subCategory : new HashSet<>(categoryToDelete.getSubCategories())) {
                subCategory.setParentCategory(null);
                categoryRepository.save(subCategory);
            }
            categoryToDelete.getSubCategories().clear();
        }
        categoryRepository.delete(categoryToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAllWithParent(pageable);
        return categoriesPage.map(CategoryResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getRootCategories() {
        List<Category> rootCategories = categoryRepository.findRootCategoriesWithSubCategories();
        return rootCategories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getSubCategoriesByParentId(Integer parentId) {
        if (!categoryRepository.existsById(parentId)) {
            throw new EntityNotFoundException("Parent Category not found with id: " + parentId);
        }
        List<Category> subCategories = categoryRepository.findByParentCategoryCategoryIdWithDetails(parentId);
        return subCategories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }
}
