package com.project.nmcnpm.dto;

public class CartItemDTO {
    private Integer productId;
    private Integer shopId;
    private String productName;
    private String color;
    private String size;
    private String productImageUrl;
    private Integer productPrice;
    private Integer productQuantity;
    public CartItemDTO() {
    }
    public CartItemDTO(Integer productId, Integer shopId, String productName, String color, String size, String productImageUrl, Integer productPrice, Integer productQuantity) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
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
    public String getProductName() {
        return productName;
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
    public Integer getProductPrice() {
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
    public void setProductName(String productName) {
        this.productName = productName;
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
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
