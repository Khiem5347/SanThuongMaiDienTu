package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductImage;

public class ProductImageResponseDTO {
    private Integer imageId;
    private Integer productId;
    private String productUrl;
    public ProductImageResponseDTO() {
    }
    public ProductImageResponseDTO(ProductImage productImage) {
        this.imageId = productImage.getImageId();
        this.productUrl = productImage.getProductUrl();
        if (productImage.getProduct() != null) {
            this.productId = productImage.getProduct().getProductId();
        }
    }
    public Integer getImageId() {
        return imageId;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
