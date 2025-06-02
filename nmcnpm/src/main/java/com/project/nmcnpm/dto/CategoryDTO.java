package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    private String categoryName;
    private Integer parentId;
    public CategoryDTO() {
    }
    public CategoryDTO(String categoryName, Integer parentId) {
        this.categoryName = categoryName;
        this.parentId = parentId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
