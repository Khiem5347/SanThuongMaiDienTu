package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Category;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseDTO {
    private Integer categoryId;
    private String categoryName;
    private Integer parentId;
    private String parentCategoryName;
    private List<SubCategoryInfo> subCategories;
    public static class SubCategoryInfo {
        private Integer id;
        private String name;
        public SubCategoryInfo(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        public Integer getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    public CategoryResponseDTO() {
    }
    public CategoryResponseDTO(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        if (category.getParentCategory() != null) {
            this.parentId = category.getParentCategory().getCategoryId();
            this.parentCategoryName = category.getParentCategory().getCategoryName();
        }
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            this.subCategories = category.getSubCategories().stream()
                    .map(sub -> new SubCategoryInfo(sub.getCategoryId(), sub.getCategoryName()))
                    .collect(Collectors.toList());
        }
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getParentCategoryName() {
        return parentCategoryName;
    }
    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
    public List<SubCategoryInfo> getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(List<SubCategoryInfo> subCategories) {
        this.subCategories = subCategories;
    }
}
