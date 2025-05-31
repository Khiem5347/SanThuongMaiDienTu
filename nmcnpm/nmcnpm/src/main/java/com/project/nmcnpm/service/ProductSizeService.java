package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ProductSizeDTO;
import com.project.nmcnpm.dto.ProductSizeResponseDTO;
import com.project.nmcnpm.entity.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductSizeService {
    ProductSize createProductSize(ProductSizeDTO productSizeDTO);
    ProductSizeResponseDTO getProductSizeById(Integer sizeId);
    ProductSize updateProductSize(Integer sizeId, ProductSizeDTO productSizeDTO);
    void deleteProductSize(Integer sizeId);
    Page<ProductSizeResponseDTO> getAllProductSizes(Pageable pageable);
    List<ProductSizeResponseDTO> getProductSizesByProductVariantId(Integer variantId);
}
