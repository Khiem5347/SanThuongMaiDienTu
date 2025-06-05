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
}
