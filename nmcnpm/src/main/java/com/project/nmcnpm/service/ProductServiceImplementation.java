package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.CategoryRepository;
import com.project.nmcnpm.dao.ProductRepository;
import com.project.nmcnpm.dao.ShopRepository;
import com.project.nmcnpm.dto.ProductDTO;
import com.project.nmcnpm.dto.ProductResponseDTO;
import com.project.nmcnpm.entity.Category;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List; 
import java.util.stream.Collectors; 
import java.util.Arrays; 

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    public ProductServiceImplementation(ProductRepository productRepository,
                                        CategoryRepository categoryRepository,
                                        ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.shopRepository = shopRepository;
    }
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        Shop shop = shopRepository.findById(productDTO.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + productDTO.getShopId()));

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductMainImageUrl(productDTO.getProductMainImageUrl());
        product.setProductMinPrice(productDTO.getProductMinPrice());
        product.setProductMaxPrice(productDTO.getProductMaxPrice());
        product.setCategory(category);
        product.setShop(shop);
        return productRepository.save(product);
    }
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        return new ProductResponseDTO(product);
    }
    @Override
    @Transactional
    public Product updateProduct(Integer productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        if (productDTO.getProductName() != null) {
            existingProduct.setProductName(productDTO.getProductName());
        }
        if (productDTO.getProductDescription() != null) {
            existingProduct.setProductDescription(productDTO.getProductDescription());
        }
        if (productDTO.getProductMainImageUrl() != null) {
            existingProduct.setProductMainImageUrl(productDTO.getProductMainImageUrl());
        }
        if (productDTO.getProductMinPrice() != null) {
            existingProduct.setProductMinPrice(productDTO.getProductMinPrice());
        }
        if (productDTO.getProductMaxPrice() != null) {
            existingProduct.setProductMaxPrice(productDTO.getProductMaxPrice());
        }
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }
        if (productDTO.getShopId() != null) {
            Shop shop = shopRepository.findById(productDTO.getShopId())
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + productDTO.getShopId()));
            existingProduct.setShop(shop);
        }
        return productRepository.save(existingProduct);
    }
    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(ProductResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getProductsByCategoryId(Integer categoryId, Pageable pageable) {
        Page<Product> productsPage = productRepository.findByCategoryCategoryId(categoryId, pageable);
        return productsPage.map(ProductResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> searchProductsByName(String name, Pageable pageable) {
        Page<Product> productsPage = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
        return productsPage.map(ProductResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getProductsByShopId(Integer shopId, Pageable pageable) {
        Page<Product> productsPage = productRepository.findByShopShopId(shopId, pageable);
        return productsPage.map(ProductResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getProductsByCategoryIds(List<Integer> categoryIds, Pageable pageable) {
        Page<Product> productsPage = productRepository.findByCategoryCategoryIdIn(categoryIds, pageable);
        return productsPage.map(ProductResponseDTO::new);
    }
}