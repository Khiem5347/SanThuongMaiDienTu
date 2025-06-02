package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Shop;
import com.project.nmcnpm.entity.User; 

public class ShopResponseDTO {
    private Integer shopId;
    private String shopName;
    private String shopDescription;
    private String shopAvatarUrl;
    private String shopAddr;
    private Integer shopRevenue;
    private Integer userId;
    private String userName; 
    private int productCount;
    private int productsInCartCount;
    private int productsInOrderCount;
    public ShopResponseDTO() {
    }
    public ShopResponseDTO(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.shopDescription = shop.getShopDescription();
        this.shopAvatarUrl = shop.getShopAvatarUrl();
        this.shopAddr = shop.getShopAddr();
        this.shopRevenue = shop.getShopRevenue();
        User user = shop.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername(); 
        }
        this.productCount = shop.getProducts() != null ? shop.getProducts().size() : 0;
        this.productsInCartCount = shop.getProductsInCarts() != null ? shop.getProductsInCarts().size() : 0;
        this.productsInOrderCount = shop.getProductsInOrders() != null ? shop.getProductsInOrders().size() : 0;
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
    public String getShopDescription() {
        return shopDescription;
    }
    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
    public String getShopAvatarUrl() {
        return shopAvatarUrl;
    }
    public void setShopAvatarUrl(String shopAvatarUrl) {
        this.shopAvatarUrl = shopAvatarUrl;
    }
    public String getShopAddr() {
        return shopAddr;
    }
    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }
    public Integer getShopRevenue() {
        return shopRevenue;
    }
    public void setShopRevenue(Integer shopRevenue) {
        this.shopRevenue = shopRevenue;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getProductCount() {
        return productCount;
    }
    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
    public int getProductsInCartCount() {
        return productsInCartCount;
    }
    public void setProductsInCartCount(int productsInCartCount) {
        this.productsInCartCount = productsInCartCount;
    }
    public int getProductsInOrderCount() {
        return productsInOrderCount;
    }
    public void setProductsInOrderCount(int productsInOrderCount) {
        this.productsInOrderCount = productsInOrderCount;
    }
}
