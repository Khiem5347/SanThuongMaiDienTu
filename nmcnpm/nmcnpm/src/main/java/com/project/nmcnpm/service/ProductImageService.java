package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ProductImageDTO;
import com.project.nmcnpm.dto.ProductImageResponseDTO;
import com.project.nmcnpm.entity.ProductImage;
import java.util.List;

public interface ProductImageService {
    ProductImage createProductImage(ProductImageDTO productImageDTO);
    ProductImageResponseDTO getProductImageById(Integer imageId);
    ProductImage updateProductImage(Integer imageId, ProductImageDTO productImageDTO);
    void deleteProductImage(Integer imageId);
    List<ProductImageResponseDTO> getProductImagesByProductId(Integer productId);
}
