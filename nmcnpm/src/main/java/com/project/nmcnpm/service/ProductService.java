package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ProductDTO;
import com.project.nmcnpm.dto.ProductResponseDTO; 
import com.project.nmcnpm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Integer productId, ProductDTO productDTO);
    void deleteProduct(Integer productId);
    ProductResponseDTO getProductById(Integer productId);
    Page<ProductResponseDTO> getAllProducts(Pageable pageable);
    Page<ProductResponseDTO> getProductsByCategoryId(Integer categoryId, Pageable pageable);
    Page<ProductResponseDTO> searchProductsByName(String name, Pageable pageable);
    Page<ProductResponseDTO> getProductsByShopId(Integer shopId, Pageable pageable);
    Page<ProductResponseDTO> getProductsByCategoryIds(List<Integer> categoryIds, Pageable pageable);
}