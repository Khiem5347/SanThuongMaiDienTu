package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ProductReviewDTO;
import com.project.nmcnpm.dto.ProductReviewResponseDTO; 
import com.project.nmcnpm.entity.ProductReview;
import com.project.nmcnpm.service.ProductReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
public class ProductReviewController {
    private final ProductReviewService productReviewService;
    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }
    @PostMapping
    public ResponseEntity<ProductReviewResponseDTO> createProductReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) {
        try {
            ProductReview createdReview = productReviewService.createProductReview(productReviewDTO);
            ProductReviewResponseDTO responseDTO = new ProductReviewResponseDTO(createdReview);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating product review: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating product review: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewResponseDTO> getProductReviewById(@PathVariable Integer id) {
        try {
            ProductReviewResponseDTO review = productReviewService.getProductReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Review not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product review by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReviewResponseDTO> updateProductReview(@PathVariable Integer id, @Valid @RequestBody ProductReviewDTO productReviewDTO) {
        try {
            ProductReview updatedReview = productReviewService.updateProductReview(id, productReviewDTO);
            // Convert the updated entity to a DTO before returning
            ProductReviewResponseDTO responseDTO = new ProductReviewResponseDTO(updatedReview);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Review or associated entity not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating product review: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewResponseDTO>> getReviewsByProductId(@PathVariable Integer productId) {
    try {
        List<ProductReviewResponseDTO> reviews = productReviewService.getProductReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    } catch (Exception e) {
        System.err.println("Error retrieving reviews for product ID " + productId + ": " + e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}