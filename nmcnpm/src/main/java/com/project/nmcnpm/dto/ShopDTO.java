package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShopDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotBlank(message = "Shop name cannot be blank")
    @Size(max = 100, message = "Shop name cannot exceed 100 characters")
    private String shopName;
    private String shopDescription;
    private String shopAvatarUrl;
    private String shopAddr;
    private Integer shopRevenue; 
    public ShopDTO() {
    }
    public ShopDTO(Integer userId, String shopName, String shopDescription, String shopAvatarUrl, String shopAddr, Integer shopRevenue) {
        this.userId = userId;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.shopAvatarUrl = shopAvatarUrl;
        this.shopAddr = shopAddr;
        this.shopRevenue = shopRevenue;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getShopName() {
        return shopName;
    }
    public String getShopDescription() {
        return shopDescription;
    }
    public String getShopAvatarUrl() {
        return shopAvatarUrl;
    }
    public String getShopAddr() {
        return shopAddr;
    }
    public Integer getShopRevenue() {
        return shopRevenue;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
    public void setShopAvatarUrl(String shopAvatarUrl) {
        this.shopAvatarUrl = shopAvatarUrl;
    }
    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }
    public void setShopRevenue(Integer shopRevenue) {
        this.shopRevenue = shopRevenue;
    }
}
