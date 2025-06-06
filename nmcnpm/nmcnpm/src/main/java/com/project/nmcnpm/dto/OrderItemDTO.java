package com.project.nmcnpm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderItemDTO {
    @NotNull(message = "Product ID cannot be null")
    private Integer productId;
    @NotNull(message = "Shop ID cannot be null for an order item")
    private Integer shopId;
    private String color;
    private String size;
    private String productImageUrl; 
    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price must be non-negative")
    private BigDecimal productPrice; 
    @NotNull(message = "Product quantity cannot be null")
    @Min(value = 1, message = "Product quantity must be at least 1")
    private Integer productQuantity;
    public OrderItemDTO() {
    }
    public OrderItemDTO(Integer productId, Integer shopId, String color, String size, String productImageUrl, BigDecimal productPrice, Integer productQuantity) {
        this.productId = productId;
        this.shopId = shopId;
        this.color = color;
        this.size = size;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
    public Integer getProductId() {
        return productId;
    }
    public Integer getShopId() {
        return shopId;
    }
    public String getColor() {
        return color;
    }
    public String getSize() {
        return size;
    }
    public String getProductImageUrl() {
        return productImageUrl;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
