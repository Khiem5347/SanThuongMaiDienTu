package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ProductVariantDTO;
import com.project.nmcnpm.dto.ProductVariantResponseDTO;
import com.project.nmcnpm.service.ProductVariantService;
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
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;
    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }
    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> createProductVariant(@Valid @RequestBody ProductVariantDTO productVariantDTO) {
        try {
            ProductVariantResponseDTO createdVariant = productVariantService.createProductVariant(productVariantDTO);
            return new ResponseEntity<>(createdVariant, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating product variant: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating product variant: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> getProductVariantById(@PathVariable Integer id) {
        try {
            ProductVariantResponseDTO variant = productVariantService.getProductVariantById(id);
            return new ResponseEntity<>(variant, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Variant not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product variant by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> updateProductVariant(@PathVariable Integer id, @Valid @RequestBody ProductVariantDTO productVariantDTO) {
        try {
            ProductVariantResponseDTO updatedVariant = productVariantService.updateProductVariant(id, productVariantDTO);
            return new ResponseEntity<>(updatedVariant, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Variant or associated entity not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating product variant: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductVariant(@PathVariable Integer id) {
        try {
            productVariantService.deleteProductVariant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Product Variant not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting product variant: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ProductVariantResponseDTO>> getAllProductVariants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductVariantResponseDTO> variants = productVariantService.getAllProductVariants(pageable);
        return new ResponseEntity<>(variants, HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariantResponseDTO>> getProductVariantsByProductId(@PathVariable Integer productId) {
        try {
            List<ProductVariantResponseDTO> variants = productVariantService.getProductVariantsByProductId(productId);
            return new ResponseEntity<>(variants, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product not found when fetching variants: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product variants by product ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
