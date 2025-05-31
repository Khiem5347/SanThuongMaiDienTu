package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductsInOrder;
import java.math.BigDecimal;

public class OrderItemResponseDTO {
    private Integer productsInOrderId;
    private Integer productId;
    private String productName;
    private Integer shopId;
    private String shopName; 
    private String color;
    private String size;
    private String productImageUrl;
    private BigDecimal productPrice;
    private Integer productQuantity;
    public OrderItemResponseDTO() {
    }
    public OrderItemResponseDTO(ProductsInOrder productsInOrder) {
        this.productsInOrderId = productsInOrder.getProductsInOrderId();
        this.productId = productsInOrder.getProduct().getProductId();
        this.productName = productsInOrder.getProductName();
        this.shopId = productsInOrder.getShop().getShopId();
        this.shopName = productsInOrder.getShop().getShopName(); 
        this.color = productsInOrder.getColor();
        this.size = productsInOrder.getSize();
        this.productImageUrl = productsInOrder.getProductImageUrl();
        this.productPrice = productsInOrder.getProductPrice();
        this.productQuantity = productsInOrder.getProductQuantity();
    }
    public Integer getProductsInOrderId() {
        return productsInOrderId;
    }
    public void setProductsInOrderId(Integer productsInOrderId) {
        this.productsInOrderId = productsInOrderId;
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
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
