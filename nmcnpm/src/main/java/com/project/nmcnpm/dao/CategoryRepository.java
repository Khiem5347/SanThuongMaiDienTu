package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.parentCategory p LEFT JOIN FETCH c.subCategories s WHERE c.categoryId = :categoryId")
    Optional<Category> findByIdWithDetails(@Param("categoryId") Integer categoryId);
    @Query(value = "SELECT c FROM Category c LEFT JOIN FETCH c.parentCategory",
           countQuery = "SELECT count(c) FROM Category c")
    Page<Category> findAllWithParent(Pageable pageable);
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories s WHERE c.parentCategory IS NULL")
    List<Category> findRootCategoriesWithSubCategories();
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.parentCategory p LEFT JOIN FETCH c.subCategories s WHERE c.parentCategory.categoryId = :parentId")
    List<Category> findByParentCategoryCategoryIdWithDetails(@Param("parentId") Integer parentId);
    List<Category> findByParentCategoryIsNull();
    List<Category> findByParentCategoryCategoryId(Integer parentId);
}
