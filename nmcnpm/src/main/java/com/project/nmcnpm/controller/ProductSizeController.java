package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ProductSizeDTO;
import com.project.nmcnpm.dto.ProductSizeResponseDTO; 
import com.project.nmcnpm.entity.ProductSize;
import com.project.nmcnpm.service.ProductSizeService;
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
@RequestMapping("/api/product-sizes")
public class ProductSizeController {
    private final ProductSizeService productSizeService;
    public ProductSizeController(ProductSizeService productSizeService) {
        this.productSizeService = productSizeService;
    }
    @PostMapping
    public ResponseEntity<ProductSizeResponseDTO> createProductSize(@Valid @RequestBody ProductSizeDTO productSizeDTO) { // Changed return type
        try {
            ProductSize createdSize = productSizeService.createProductSize(productSizeDTO);
            ProductSizeResponseDTO responseDTO = productSizeService.getProductSizeById(createdSize.getSizeId()); // Assuming getSizeId() exists in ProductSize entity
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating product size: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating product size: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductSizeResponseDTO> getProductSizeById(@PathVariable Integer id) {
        try {
            ProductSizeResponseDTO size = productSizeService.getProductSizeById(id);
            return new ResponseEntity<>(size, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Size not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product size by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductSizeResponseDTO> updateProductSize(@PathVariable Integer id, @Valid @RequestBody ProductSizeDTO productSizeDTO) { // Changed return type
        try {
            ProductSize updatedSize = productSizeService.updateProductSize(id, productSizeDTO);
            ProductSizeResponseDTO responseDTO = productSizeService.getProductSizeById(updatedSize.getSizeId()); // Assuming getSizeId() exists in ProductSize entity
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Size or associated entity not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating product size: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductSize(@PathVariable Integer id) {
        try {
            productSizeService.deleteProductSize(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Size not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting product size: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ProductSizeResponseDTO>> getAllProductSizes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductSizeResponseDTO> sizes = productSizeService.getAllProductSizes(pageable);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }
    @GetMapping("/variant/{variantId}")
    public ResponseEntity<List<ProductSizeResponseDTO>> getProductSizesByProductVariantId(@PathVariable Integer variantId) {
        try {
            List<ProductSizeResponseDTO> sizes = productSizeService.getProductSizesByProductVariantId(variantId);
            return new ResponseEntity<>(sizes, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Variant not found when fetching sizes: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product sizes by product variant ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
