package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ProductDTO;
import com.project.nmcnpm.dto.ProductResponseDTO;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List; 
import java.util.Arrays; 
import java.util.stream.Collectors; 

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product createdProduct = productService.createProduct(productDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating product: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating product: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer id) {
        try {
            ProductResponseDTO productDTO = productService.getProductById(id);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting product by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product or associated entity not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating product: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Product not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting product: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.getProductsByCategoryId(categoryId, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/category/by-categoryId")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByCategoryIds(
            @RequestParam("categoryIds") String categoryIds, // Expecting comma-separated string
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Integer> ids = Arrays.stream(categoryIds.split(","))
                                      .map(Integer::parseInt)
                                      .collect(Collectors.toList());
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductResponseDTO> products = productService.getProductsByCategoryIds(ids, pageable);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.err.println("Invalid category ID format: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error getting products by category IDs: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.searchProductsByName(name, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByShopId(
            @PathVariable Integer shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.getProductsByShopId(shopId, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}