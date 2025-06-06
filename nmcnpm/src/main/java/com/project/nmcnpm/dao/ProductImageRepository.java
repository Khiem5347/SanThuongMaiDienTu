package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query("SELECT pi FROM ProductImage pi JOIN FETCH pi.product p WHERE pi.imageId = :imageId")
    Optional<ProductImage> findByIdWithProduct(@Param("imageId") Integer imageId);
    @Query("SELECT pi FROM ProductImage pi JOIN FETCH pi.product p WHERE pi.product.productId = :productId")
    List<ProductImage> findByProductProductIdWithProduct(@Param("productId") Integer productId);
    List<ProductImage> findByProductProductId(Integer productId);
}
