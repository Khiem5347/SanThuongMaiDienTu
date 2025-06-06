package com.project.nmcnpm.dto;


public class ProductDTO {
    private String productName;
    private String productDescription;
    private String productMainImageUrl;
    private Integer productMinPrice; 
    private Integer productMaxPrice; 
    private Integer categoryId;      
    private Integer shopId;         
    public ProductDTO() {
    }
    public ProductDTO(String productName, String productDescription, String productMainImageUrl,
                      Integer productMinPrice, Integer productMaxPrice, Integer categoryId, Integer shopId) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productMainImageUrl = productMainImageUrl;
        this.productMinPrice = productMinPrice;
        this.productMaxPrice = productMaxPrice;
        this.categoryId = categoryId;
        this.shopId = shopId;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public String getProductMainImageUrl() {
        return productMainImageUrl;
    }
    public Integer getProductMinPrice() {
        return productMinPrice;
    }
    public Integer getProductMaxPrice() {
        return productMaxPrice;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public Integer getShopId() {
        return shopId;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public void setProductMainImageUrl(String productMainImageUrl) {
        this.productMainImageUrl = productMainImageUrl;
    }
    public void setProductMinPrice(Integer productMinPrice) {
        this.productMinPrice = productMinPrice;
    }
    public void setProductMaxPrice(Integer productMaxPrice) {
        this.productMaxPrice = productMaxPrice;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}