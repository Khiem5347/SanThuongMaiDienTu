package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryCategoryId(@Param("id") Integer id, Pageable pageable);
    Page<Product> findByProductNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    Page<Product> findByShopShopId(@Param("shopId") Integer shopId, Pageable pageable);
}