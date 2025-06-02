package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotNull;

public class ShippingLinkDTO {
    @NotNull(message = "Provider ID cannot be null")
    private Integer providerId;
    @NotNull(message = "Product ID cannot be null")
    private Integer productId;
    public ShippingLinkDTO() {
    }
    public ShippingLinkDTO(Integer providerId, Integer productId) {
        this.providerId = providerId;
        this.productId = productId;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
