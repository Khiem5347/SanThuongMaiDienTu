package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByParentCategoryIsNull(); 
    List<Category> findByParentCategoryCategoryId(Integer parentId); 
}