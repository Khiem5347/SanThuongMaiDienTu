package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.CategoryDTO;
import com.project.nmcnpm.dto.CategoryResponseDTO;
import com.project.nmcnpm.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List; 
public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Integer categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Integer categoryId);
    CategoryResponseDTO getCategoryById(Integer categoryId);
    Page<CategoryResponseDTO> getAllCategories(Pageable pageable);
    List<CategoryResponseDTO> getRootCategories(); 
    List<CategoryResponseDTO> getSubCategoriesByParentId(Integer parentId); 
}
