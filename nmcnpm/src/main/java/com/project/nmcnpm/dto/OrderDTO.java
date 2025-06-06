package com.project.nmcnpm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class OrderDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotNull(message = "Address ID cannot be null")
    private Integer addressId;
    @Size(min = 1, message = "Order must contain at least one item")
    @Valid
    private List<OrderItemDTO> items;
    public OrderDTO() {
    }
    public OrderDTO(Integer userId, Integer addressId, List<OrderItemDTO> items) {
        this.userId = userId;
        this.addressId = addressId;
        this.items = items;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public static class OrderItemDTO {
        @NotNull(message = "Product ID cannot be null")
        private Integer productId;
        @NotNull(message = "Shop ID cannot be null")
        private Integer shopId; 
        private String productName;
        private String color;
        private String size;
        private String productImageUrl;
        @NotNull(message = "Product price cannot be null")
        private Integer productPrice; 
        @NotNull(message = "Product quantity cannot be null")
        private Integer productQuantity;
        private Integer voucherId;
        private Integer providerId; 

        public OrderItemDTO() {
        }

        public OrderItemDTO(Integer productId, Integer shopId, String productName, String color, String size,
                            String productImageUrl, Integer productPrice, Integer productQuantity, Integer voucherId, Integer providerId) { // CHANGED productPrice, ADDED providerId
            this.productId = productId;
            this.shopId = shopId;
            this.productName = productName;
            this.color = color;
            this.size = size;
            this.productImageUrl = productImageUrl;
            this.productPrice = productPrice;
            this.productQuantity = productQuantity;
            this.voucherId = voucherId;
            this.providerId = providerId; 
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
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

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getProductImageUrl() {
            return productImageUrl;
        }

        public void setProductImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
        }

        public Integer getProductPrice() { 
            return productPrice;
        }

        public void setProductPrice(Integer productPrice) { 
            this.productPrice = productPrice;
        }

        public Integer getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(Integer productQuantity) {
            this.productQuantity = productQuantity;
        }

        public Integer getVoucherId() {
            return voucherId;
        }

        public void setVoucherId(Integer voucherId) {
            this.voucherId = voucherId;
        }

        public Integer getProviderId() { 
            return providerId;
        }

        public void setProviderId(Integer providerId) { 
            this.providerId = providerId;
        }
    }
}