package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductsInCart;

public class CartItemResponseDTO {
    private Integer productsInCartId; 
    private Integer productId;
    private String productName;
    private Integer shopId;
    private String shopName;
    private String color;
    private String size;
    private String productImageUrl;
    private Integer productPrice;
    private Integer productQuantity;
    public CartItemResponseDTO() {
    }
    public CartItemResponseDTO(ProductsInCart productsInCart) {
        this.productsInCartId = productsInCart.getProductsInCartId();
        this.productId = productsInCart.getProduct().getProductId();
        this.productName = productsInCart.getProduct().getProductName(); 
        this.shopId = productsInCart.getShop().getShopId();
        this.shopName = productsInCart.getShop().getShopName(); 
        this.color = productsInCart.getColor();
        this.size = productsInCart.getSize();
        this.productImageUrl = productsInCart.getProductImageUrl();
        this.productPrice = productsInCart.getProductPrice();
        this.productQuantity = productsInCart.getProductQuantity();
    }
    public Integer getProductsInCartId() {
        return productsInCartId;
    }
    public void setProductsInCartId(Integer productsInCartId) {
        this.productsInCartId = productsInCartId;
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
}
