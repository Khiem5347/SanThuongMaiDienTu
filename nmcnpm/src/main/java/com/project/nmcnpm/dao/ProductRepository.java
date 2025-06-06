package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.Category; 
import com.project.nmcnpm.entity.Shop;     
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.shop s WHERE p.productId = :productId")
    Optional<Product> findByIdWithCategoryAndShop(@Param("productId") Integer productId);
    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.shop s",
           countQuery = "SELECT count(p) FROM Product p")
    Page<Product> findAllWithCategoryAndShop(Pageable pageable);
    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.shop s WHERE c.categoryId = :categoryId",
           countQuery = "SELECT count(p) FROM Product p WHERE p.category.categoryId = :categoryId")
    Page<Product> findByCategoryCategoryIdWithCategoryAndShop(@Param("categoryId") Integer categoryId, Pageable pageable);
    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.shop s WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))",
           countQuery = "SELECT count(p) FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByProductNameContainingIgnoreCaseWithCategoryAndShop(@Param("name") String name, Pageable pageable);
    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.shop s WHERE s.shopId = :shopId",
           countQuery = "SELECT count(p) FROM Product p WHERE p.shop.shopId = :shopId")
    Page<Product> findByShopShopIdWithCategoryAndShop(@Param("shopId") Integer shopId, Pageable pageable);
    Page<Product> findByCategoryCategoryId(Integer categoryId, Pageable pageable);
    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByShopShopId(Integer shopId, Pageable pageable);
}
