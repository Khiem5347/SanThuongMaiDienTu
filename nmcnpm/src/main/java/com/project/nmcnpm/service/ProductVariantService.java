package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ProductVariantDTO;
import com.project.nmcnpm.dto.ProductVariantResponseDTO;
import com.project.nmcnpm.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductVariantService {
    ProductVariant createProductVariant(ProductVariantDTO productVariantDTO);
    ProductVariantResponseDTO getProductVariantById(Integer variantId);
    ProductVariant updateProductVariant(Integer variantId, ProductVariantDTO productVariantDTO);
    void deleteProductVariant(Integer variantId);
    Page<ProductVariantResponseDTO> getAllProductVariants(Pageable pageable);
    List<ProductVariantResponseDTO> getProductVariantsByProductId(Integer productId);
}
