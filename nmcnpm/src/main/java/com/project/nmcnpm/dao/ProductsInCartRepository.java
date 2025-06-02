package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductsInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsInCartRepository extends JpaRepository<ProductsInCart, Integer> {
    Optional<ProductsInCart> findByCartCartIdAndProductProductId(Integer cartId, Integer productId);
    Optional<ProductsInCart> findByCartCartIdAndProductProductIdAndColorAndSize(Integer cartId, Integer productId, String color, String size);
}
