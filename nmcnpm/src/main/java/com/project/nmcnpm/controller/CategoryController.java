package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.CategoryDTO;
import com.project.nmcnpm.dto.CategoryResponseDTO;
import com.project.nmcnpm.entity.Category;
import com.project.nmcnpm.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category createdCategory = categoryService.createCategory(categoryDTO);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating category: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating category: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Integer id) {
        try {
            CategoryResponseDTO category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Category not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting category by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Category or parent category not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating category: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Category not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting category: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponseDTO> categories = categoryService.getAllCategories(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/roots")
    public ResponseEntity<List<CategoryResponseDTO>> getRootCategories() {
        try {
            List<CategoryResponseDTO> rootCategories = categoryService.getRootCategories();
            return new ResponseEntity<>(rootCategories, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Internal server error getting root categories: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/parent/{parentId}/subcategories")
    public ResponseEntity<List<CategoryResponseDTO>> getSubCategoriesByParentId(@PathVariable Integer parentId) {
        try {
            List<CategoryResponseDTO> subCategories = categoryService.getSubCategoriesByParentId(parentId);
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Parent Category not found for subcategories: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting subcategories: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
