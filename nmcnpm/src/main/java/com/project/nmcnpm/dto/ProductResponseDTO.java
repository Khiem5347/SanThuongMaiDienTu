package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.Category; 
import com.project.nmcnpm.entity.Shop;     

public class ProductResponseDTO {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer productStar;
    private Integer productReview;
    private Integer productSold;
    private String productMainImageUrl;
    private Integer productMinPrice;
    private Integer productMaxPrice;
    private Integer numberOfLikes;
    private Integer numOfProductStar;
    private Integer categoryId;
    private String categoryName;
    private Integer shopId;
    private String shopName;
    private Integer remainingQuantity;
    public ProductResponseDTO() {
    }
    public ProductResponseDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productStar = product.getProductStar();
        this.productReview = product.getProductReview();
        this.productSold = product.getProductSold();
        this.productMainImageUrl = product.getProductMainImageUrl();
        this.productMinPrice = product.getProductMinPrice();
        this.productMaxPrice = product.getProductMaxPrice();
        this.numberOfLikes = product.getNumberOfLikes();
        this.numOfProductStar = product.getNumOfProductStar();
        this.remainingQuantity = product.getRemainingQuantity();
        Category category = product.getCategory();
        if (category != null) {
            this.categoryId = category.getCategoryId();
            this.categoryName = category.getCategoryName(); 
        }
        Shop shop = product.getShop();
        if (shop != null) {
            this.shopId = shop.getShopId();
            this.shopName = shop.getShopName(); 
        }
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
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public Integer getProductStar() {
        return productStar;
    }
    public void setProductStar(Integer productStar) {
        this.productStar = productStar;
    }
    public Integer getProductReview() {
        return productReview;
    }
    public void setProductReview(Integer productReview) {
        this.productReview = productReview;
    }
    public Integer getProductSold() {
        return productSold;
    }
    public void setProductSold(Integer productSold) {
        this.productSold = productSold;
    }
    public String getProductMainImageUrl() {
        return productMainImageUrl;
    }
    public void setProductMainImageUrl(String productMainImageUrl) {
        this.productMainImageUrl = productMainImageUrl;
    }
    public Integer getProductMinPrice() {
        return productMinPrice;
    }
    public void setProductMinPrice(Integer productMinPrice) {
        this.productMinPrice = productMinPrice;
    }
    public Integer getProductMaxPrice() {
        return productMaxPrice;
    }
    public void setProductMaxPrice(Integer productMaxPrice) {
        this.productMaxPrice = productMaxPrice;
    }
    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }
    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
    public Integer getNumOfProductStar() {
        return numOfProductStar;
    }
    public void setNumOfProductStar(Integer numOfProductStar) {
        this.numOfProductStar = numOfProductStar;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    public Integer getRemainingQuantity() { 
        return remainingQuantity;
    }
    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}