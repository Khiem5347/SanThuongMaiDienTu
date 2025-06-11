package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subCategories = new HashSet<>(); 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>(); 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppliedVoucher> appliedVouchers = new HashSet<>();
    public Category() {
    }
    public Category(Integer categoryId, String categoryName, Category parentCategory,
                    Set<Category> subCategories, Set<Product> products,
                    Set<AppliedVoucher> appliedVouchers) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.products = products;
        this.appliedVouchers = appliedVouchers;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public Category getParentCategory() {
        return parentCategory;
    }
    public Set<Category> getSubCategories() {
        return subCategories;
    }
    public Set<Product> getProducts() {
        return products;
    }
    public Set<AppliedVoucher> getAppliedVouchers() {
        return appliedVouchers;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public void setAppliedVouchers(Set<AppliedVoucher> appliedVouchers) {
        this.appliedVouchers = appliedVouchers;
    }
    public void addSubCategory(Category subCategory) {
        if (subCategory != null) {
            if (this.subCategories == null) {
                this.subCategories = new HashSet<>();
            }
            this.subCategories.add(subCategory);
            subCategory.setParentCategory(this);
        }
    }
    public void removeSubCategory(Category subCategory) {
        if (subCategory != null && this.subCategories != null) {
            this.subCategories.remove(subCategory);
            subCategory.setParentCategory(null);
        }
    }
    public void addProduct(Product product) {
        if (product != null) {
            if (this.products == null) {
                this.products = new HashSet<>();
            }
            this.products.add(product);
            product.setCategory(this);
        }
    }
    public void removeProduct(Product product) {
        if (product != null && this.products != null) {
            this.products.remove(product);
            product.setCategory(null);
        }
    }
    public void addAppliedVoucher(AppliedVoucher appliedVoucher) {
        if (appliedVoucher != null) {
            if (this.appliedVouchers == null) {
                this.appliedVouchers = new HashSet<>();
            }
            this.appliedVouchers.add(appliedVoucher);
            appliedVoucher.setCategory(this);
        }
    }
    public void removeAppliedVoucher(AppliedVoucher appliedVoucher) {
        if (appliedVoucher != null && this.appliedVouchers != null) {
            this.appliedVouchers.remove(appliedVoucher);
            appliedVoucher.setCategory(null);
        }
    }
}