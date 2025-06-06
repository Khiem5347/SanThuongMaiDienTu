package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ProductImageRepository;
import com.project.nmcnpm.dao.ProductRepository;
import com.project.nmcnpm.dto.ProductImageDTO;
import com.project.nmcnpm.dto.ProductImageResponseDTO;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ProductImage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImplementation implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public ProductImageServiceImplementation(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductImage createProductImage(ProductImageDTO productImageDTO) {
        Product product = productRepository.findById(productImageDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productImageDTO.getProductId()));

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setProductUrl(productImageDTO.getProductUrl());

        return productImageRepository.save(productImage);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductImageResponseDTO getProductImageById(Integer imageId) {
        ProductImage productImage = productImageRepository.findByIdWithProduct(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Product Image not found with id: " + imageId));
        return new ProductImageResponseDTO(productImage);
    }

    @Override
    @Transactional
    public ProductImage updateProductImage(Integer imageId, ProductImageDTO productImageDTO) {
        ProductImage existingImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Product Image not found with id: " + imageId));

        if (productImageDTO.getProductUrl() != null && !productImageDTO.getProductUrl().isEmpty()) {
            existingImage.setProductUrl(productImageDTO.getProductUrl());
        }
        if (productImageDTO.getProductId() != null && !existingImage.getProduct().getProductId().equals(productImageDTO.getProductId())) {
            Product newProduct = productRepository.findById(productImageDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product not found with id: " + productImageDTO.getProductId()));
            existingImage.setProduct(newProduct);
        }

        return productImageRepository.save(existingImage);
    }

    @Override
    @Transactional
    public void deleteProductImage(Integer imageId) {
        if (!productImageRepository.existsById(imageId)) {
            throw new EntityNotFoundException("Product Image not found with id: " + imageId);
        }
        productImageRepository.deleteById(imageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageResponseDTO> getProductImagesByProductId(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        List<ProductImage> images = productImageRepository.findByProductProductIdWithProduct(productId);
        return images.stream()
                .map(ProductImageResponseDTO::new)
                .collect(Collectors.toList());
    }
}
