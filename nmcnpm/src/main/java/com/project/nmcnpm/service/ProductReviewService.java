package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ProductReviewDTO;
import com.project.nmcnpm.dto.ProductReviewResponseDTO;
import com.project.nmcnpm.entity.ProductReview; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductReviewService {
    ProductReview createProductReview(ProductReviewDTO productReviewDTO);
    ProductReviewResponseDTO getProductReviewById(Integer reviewId);
    ProductReview updateProductReview(Integer reviewId, ProductReviewDTO productReviewDTO);
    void deleteProductReview(Integer reviewId);
    Page<ProductReviewResponseDTO> getAllProductReviews(Pageable pageable);
    List<ProductReviewResponseDTO> getProductReviewsByProductId(Integer productId);
    List<ProductReviewResponseDTO> getProductReviewsByUserId(Integer userId);
}
