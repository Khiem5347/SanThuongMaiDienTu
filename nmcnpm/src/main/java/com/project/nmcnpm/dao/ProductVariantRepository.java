package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    @Query("SELECT DISTINCT pv FROM ProductVariant pv JOIN FETCH pv.product p LEFT JOIN FETCH pv.productSizes ps WHERE pv.variantId = :variantId")
    Optional<ProductVariant> findByIdWithProductAndSizes(@Param("variantId") Integer variantId);
    @Query(value = "SELECT DISTINCT pv FROM ProductVariant pv JOIN FETCH pv.product p LEFT JOIN FETCH pv.productSizes ps",
           countQuery = "SELECT count(pv) FROM ProductVariant pv")
    Page<ProductVariant> findAllWithProductAndSizes(Pageable pageable);
    @Query("SELECT DISTINCT pv FROM ProductVariant pv JOIN FETCH pv.product p LEFT JOIN FETCH pv.productSizes ps WHERE pv.product.productId = :productId")
    List<ProductVariant> findByProductProductIdWithProductAndSizes(@Param("productId") Integer productId);
    List<ProductVariant> findByProductProductId(Integer productId);
}
