package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductsInOrder;
import java.math.BigDecimal; 
public class OrderItemResponseDTO {
    private Integer productsInOrderId;
    private String productName;
    private Integer shopId;
    private String shopName;
    private String color;
    private String size;
    private String productImageUrl;
    private Integer productPrice; 
    private Integer productQuantity;
    private Integer voucherId;
    private Integer providerId;

    public OrderItemResponseDTO() {
    }

    public OrderItemResponseDTO(ProductsInOrder productsInOrder) {
        this.productsInOrderId = productsInOrder.getProductsInOrderId();
        this.productName = productsInOrder.getProductName();
        if (productsInOrder.getShop() != null) {
            this.shopId = productsInOrder.getShop().getShopId();
            this.shopName = productsInOrder.getShop().getShopName();
        } else {
            this.shopId = null; 
            this.shopName = null; 
        }

        this.color = productsInOrder.getColor();
        this.size = productsInOrder.getSize();
        this.productImageUrl = productsInOrder.getProductImageUrl();
        this.productPrice = productsInOrder.getProductPrice();
        this.productQuantity = productsInOrder.getProductQuantity();
        if (productsInOrder.getVoucher() != null) {
            this.voucherId = productsInOrder.getVoucher().getVoucherId();
        } else {
            this.voucherId = null; 
        }
        this.providerId = productsInOrder.getProviderId();
    }

    public Integer getProductsInOrderId() {
        return productsInOrderId;
    }

    public void setProductsInOrderId(Integer productsInOrderId) {
        this.productsInOrderId = productsInOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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