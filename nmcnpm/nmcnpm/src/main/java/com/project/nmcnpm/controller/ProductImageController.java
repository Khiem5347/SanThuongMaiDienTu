package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ProductImageDTO;
import com.project.nmcnpm.dto.ProductImageResponseDTO;
import com.project.nmcnpm.entity.ProductImage;
import com.project.nmcnpm.service.ProductImageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {
    private final ProductImageService productImageService;
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }
    @PostMapping
    public ResponseEntity<ProductImage> createProductImage(@Valid @RequestBody ProductImageDTO productImageDTO) {
        try {
            ProductImage createdImage = productImageService.createProductImage(productImageDTO);
            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating product image: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Internal server error creating product image: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductImageResponseDTO> getProductImageById(@PathVariable Integer id) {
        try {
            ProductImageResponseDTO image = productImageService.getProductImageById(id);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Image not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product image by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@PathVariable Integer id, @Valid @RequestBody ProductImageDTO productImageDTO) {
        try {
            ProductImage updatedImage = productImageService.updateProductImage(id, productImageDTO);
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Image or associated product not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating product image: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        try {
            productImageService.deleteProductImage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Image not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting product image: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductImageResponseDTO>> getProductImagesByProductId(@PathVariable Integer productId) {
        try {
            List<ProductImageResponseDTO> images = productImageService.getProductImagesByProductId(productId);
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product not found when fetching images: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product images by product ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
