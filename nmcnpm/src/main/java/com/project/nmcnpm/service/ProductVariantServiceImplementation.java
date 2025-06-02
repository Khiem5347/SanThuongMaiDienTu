package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ProductVariantRepository;
import com.project.nmcnpm.dao.ProductRepository; 
import com.project.nmcnpm.dao.ProductSizeRepository; 
import com.project.nmcnpm.dto.ProductSizeDTO;
import com.project.nmcnpm.dto.ProductVariantDTO;
import com.project.nmcnpm.dto.ProductVariantResponseDTO;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ProductSize;
import com.project.nmcnpm.entity.ProductVariant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImplementation implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository productSizeRepository; 
    public ProductVariantServiceImplementation(ProductVariantRepository productVariantRepository,
                                               ProductRepository productRepository,
                                               ProductSizeRepository productSizeRepository) {
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
        this.productSizeRepository = productSizeRepository;
    }
    @Override
    @Transactional
    public ProductVariant createProductVariant(ProductVariantDTO productVariantDTO) {
        Product product = productRepository.findById(productVariantDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productVariantDTO.getProductId()));

        ProductVariant productVariant = new ProductVariant();
        productVariant.setProduct(product);
        productVariant.setColor(productVariantDTO.getColor());
        productVariant.setImageUrl(productVariantDTO.getImageUrl());
        if (productVariantDTO.getProductSizes() != null && !productVariantDTO.getProductSizes().isEmpty()) {
            for (ProductSizeDTO sizeDTO : productVariantDTO.getProductSizes()) {
                ProductSize productSize = new ProductSize();
                productSize.setSize(sizeDTO.getSize());
                productSize.setPrice(sizeDTO.getPrice());
                productSize.setQuantity(sizeDTO.getQuantity());
                productVariant.addProductSize(productSize); 
            }
        }
        ProductVariant savedVariant = productVariantRepository.save(productVariant);
        updateProductStockAndPrice(product);
        return savedVariant;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariantResponseDTO getProductVariantById(Integer variantId) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + variantId));
        return new ProductVariantResponseDTO(productVariant);
    }
    @Override
    @Transactional
    public ProductVariant updateProductVariant(Integer variantId, ProductVariantDTO productVariantDTO) {
        ProductVariant existingVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + variantId));
        Product originalProduct = existingVariant.getProduct(); 
        if (productVariantDTO.getColor() != null && !productVariantDTO.getColor().isEmpty()) {
            existingVariant.setColor(productVariantDTO.getColor());
        }
        if (productVariantDTO.getImageUrl() != null) {
            existingVariant.setImageUrl(productVariantDTO.getImageUrl());
        }
        if (productVariantDTO.getProductId() != null && !existingVariant.getProduct().getProductId().equals(productVariantDTO.getProductId())) {
            Product newProduct = productRepository.findById(productVariantDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product not found with id: " + productVariantDTO.getProductId()));
            existingVariant.setProduct(newProduct);
            updateProductStockAndPrice(originalProduct); 
            updateProductStockAndPrice(newProduct); 
        }
        if (productVariantDTO.getProductSizes() != null) {
            if (existingVariant.getProductSizes() != null) {
                productSizeRepository.deleteAll(existingVariant.getProductSizes());
                existingVariant.getProductSizes().clear();
            }
            for (ProductSizeDTO sizeDTO : productVariantDTO.getProductSizes()) {
                ProductSize newSize = new ProductSize();
                newSize.setSize(sizeDTO.getSize());
                newSize.setPrice(sizeDTO.getPrice());
                newSize.setQuantity(sizeDTO.getQuantity());
                existingVariant.addProductSize(newSize);
            }
        }
        ProductVariant updatedVariant = productVariantRepository.save(existingVariant);
        updateProductStockAndPrice(updatedVariant.getProduct());
        return updatedVariant;
    }
    @Override
    @Transactional
    public void deleteProductVariant(Integer variantId) {
        ProductVariant variantToDelete = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + variantId));
        Product product = variantToDelete.getProduct(); 
        productVariantRepository.delete(variantToDelete);
        updateProductStockAndPrice(product);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductVariantResponseDTO> getAllProductVariants(Pageable pageable) {
        Page<ProductVariant> variantsPage = productVariantRepository.findAll(pageable);
        return variantsPage.map(ProductVariantResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductVariantResponseDTO> getProductVariantsByProductId(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        List<ProductVariant> variants = productVariantRepository.findByProductProductId(productId);
        return variants.stream()
                .map(ProductVariantResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional 
    private void updateProductStockAndPrice(Product product) {
        List<ProductVariant> variantsForProduct = productVariantRepository.findByProductProductId(product.getProductId());
        int totalStock = 0;
        Integer minPrice = null;
        Integer maxPrice = null;
        for (ProductVariant variant : variantsForProduct) {
            if (variant.getProductSizes() != null) {
                for (ProductSize size : variant.getProductSizes()) {
                    totalStock += size.getQuantity();
                    Integer currentPrice = size.getPrice().intValue();
                    if (minPrice == null || currentPrice < minPrice) {
                        minPrice = currentPrice;
                    }
                    if (maxPrice == null || currentPrice > maxPrice) {
                        maxPrice = currentPrice;
                    }
                }
            }
        }
        product.setProductStock(Integer.valueOf(totalStock));
        product.setProductMinPrice(minPrice != null ? minPrice : 0);
        product.setProductMaxPrice(maxPrice != null ? maxPrice : 0);
        productRepository.save(product); 
    }
}
