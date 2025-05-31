package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductImageDTO {
    @NotNull(message = "Product ID cannot be null")
    private Integer productId;
    @NotBlank(message = "Product URL cannot be blank")
    @Size(max = 255, message = "Product URL cannot exceed 255 characters")
    private String productUrl;
    public ProductImageDTO() {
    }
    public ProductImageDTO(Integer productId, String productUrl) {
        this.productId = productId;
        this.productUrl = productUrl;
    }
    public Integer getProductId() {
        return productId;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
