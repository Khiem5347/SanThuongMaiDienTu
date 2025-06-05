package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductVariant;
import com.project.nmcnpm.entity.Product; 
import java.util.List;
import java.util.stream.Collectors;

public class ProductVariantResponseDTO {
    private Integer variantId;
    private Integer productId;
    private String productName; 
    private String color;
    private String imageUrl;
    private List<ProductSizeResponseDTO> productSizes; 
    public ProductVariantResponseDTO() {
    }
    public ProductVariantResponseDTO(ProductVariant productVariant) {
        this.variantId = productVariant.getVariantId();
        this.color = productVariant.getColor();
        this.imageUrl = productVariant.getImageUrl();
        Product product = productVariant.getProduct();
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName(); 
        }
        if (productVariant.getProductSizes() != null && !productVariant.getProductSizes().isEmpty()) {
            this.productSizes = productVariant.getProductSizes().stream()
                    .map(ProductSizeResponseDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.productSizes = List.of();
        }
    }
    public Integer getVariantId() {
        return variantId;
    }
    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public List<ProductSizeResponseDTO> getProductSizes() {
        return productSizes;
    }
    public void setProductSizes(List<ProductSizeResponseDTO> productSizes) {
        this.productSizes = productSizes;
    }
}
