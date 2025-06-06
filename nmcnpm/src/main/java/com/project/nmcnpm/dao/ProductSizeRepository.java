package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    @Query("SELECT ps FROM ProductSize ps JOIN FETCH ps.productVariant pv JOIN FETCH pv.product p WHERE ps.sizeId = :sizeId")
    Optional<ProductSize> findByIdWithVariantAndProduct(@Param("sizeId") Integer sizeId);
    @Query(value = "SELECT ps FROM ProductSize ps JOIN FETCH ps.productVariant pv JOIN FETCH pv.product p",
           countQuery = "SELECT count(ps) FROM ProductSize ps")
    Page<ProductSize> findAllWithVariantAndProduct(Pageable pageable);
    @Query("SELECT ps FROM ProductSize ps JOIN FETCH ps.productVariant pv JOIN FETCH pv.product p WHERE ps.productVariant.variantId = :variantId")
    List<ProductSize> findByProductVariantVariantIdWithVariantAndProduct(@Param("variantId") Integer variantId);
    ProductSize findByProductVariantVariantId(Integer variantId);
}
