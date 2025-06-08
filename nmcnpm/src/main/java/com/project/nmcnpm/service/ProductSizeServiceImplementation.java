package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ProductSizeRepository;
import com.project.nmcnpm.dao.ProductVariantRepository; 
import com.project.nmcnpm.dao.ProductRepository; 
import com.project.nmcnpm.dto.ProductSizeDTO;
import com.project.nmcnpm.dto.ProductSizeResponseDTO;
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
public class ProductSizeServiceImplementation implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository; 
    public ProductSizeServiceImplementation(ProductSizeRepository productSizeRepository,
                                            ProductVariantRepository productVariantRepository,
                                            ProductRepository productRepository) {
        this.productSizeRepository = productSizeRepository;
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
    }
    @Override
    @Transactional
    public ProductSize createProductSize(ProductSizeDTO productSizeDTO) {
        ProductVariant productVariant = productVariantRepository.findById(productSizeDTO.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + productSizeDTO.getProductVariantId()));

        ProductSize productSize = new ProductSize();
        productSize.setProductVariant(productVariant);
        productSize.setSize(productSizeDTO.getSize());
        productSize.setPrice(productSizeDTO.getPrice());
        productSize.setQuantity(productSizeDTO.getQuantity());
        productVariant.addProductSize(productSize);
        ProductSize savedSize = productSizeRepository.save(productSize);
        updateProductStockAndPrice(productVariant.getProduct());
        return savedSize;
    }
    @Override
    @Transactional(readOnly = true)
    public ProductSizeResponseDTO getProductSizeById(Integer sizeId) {
        ProductSize productSize = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));
        return new ProductSizeResponseDTO(productSize);
    }

    @Override
    @Transactional
    public ProductSize updateProductSize(Integer sizeId, ProductSizeDTO productSizeDTO) {
        ProductSize existingSize = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));
        Product originalProduct = existingSize.getProductVariant().getProduct();
        if (productSizeDTO.getSize() != null && !productSizeDTO.getSize().isEmpty()) {
            existingSize.setSize(productSizeDTO.getSize());
        }
        if (productSizeDTO.getPrice() != null) {
            existingSize.setPrice(productSizeDTO.getPrice());
        }
        if (productSizeDTO.getQuantity() != null) {
            existingSize.setQuantity(productSizeDTO.getQuantity());
        }
        if (productSizeDTO.getProductVariantId() != null &&
            !existingSize.getProductVariant().getVariantId().equals(productSizeDTO.getProductVariantId())) {
            ProductVariant newVariant = productVariantRepository.findById(productSizeDTO.getProductVariantId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product Variant not found with id: " + productSizeDTO.getProductVariantId()));

            existingSize.setProductVariant(newVariant);
            newVariant.addProductSize(existingSize);
            updateProductStockAndPrice(originalProduct); 
            updateProductStockAndPrice(newVariant.getProduct()); 
        }
        ProductSize updatedSize = productSizeRepository.save(existingSize);
        updateProductStockAndPrice(updatedSize.getProductVariant().getProduct());
        return updatedSize;
    }
    @Override
    @Transactional
    public void deleteProductSize(Integer sizeId) {
        ProductSize sizeToDelete = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));
        Product product = sizeToDelete.getProductVariant().getProduct(); 
        if (sizeToDelete.getProductVariant() != null) {
            sizeToDelete.getProductVariant().removeProductSize(sizeToDelete);
        }
        productSizeRepository.delete(sizeToDelete);
        updateProductStockAndPrice(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSizeResponseDTO> getAllProductSizes(Pageable pageable) {
        Page<ProductSize> sizesPage = productSizeRepository.findAll(pageable);
        return sizesPage.map(ProductSizeResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductSizeResponseDTO> getProductSizesByProductVariantId(Integer variantId) {
        if (!productVariantRepository.existsById(variantId)) {
            throw new EntityNotFoundException("Product Variant not found with id: " + variantId);
        }
        List<ProductSize> sizes = productSizeRepository.findByProductVariantVariantId(variantId);
        return sizes.stream()
                .map(ProductSizeResponseDTO::new)
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
