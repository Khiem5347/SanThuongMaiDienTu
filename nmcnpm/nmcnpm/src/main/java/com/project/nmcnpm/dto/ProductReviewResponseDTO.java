package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductReview;
import com.project.nmcnpm.entity.Product; 
import com.project.nmcnpm.entity.User;   
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductReviewResponseDTO {
    private Integer reviewId;
    private Integer productId;
    private String productName; 
    private Integer userId;
    private String userName; 
    private Integer rating;
    private String reviewText;
    private Date reviewDate;
    private List<ReviewImageResponseDTO> reviewImages;
    public ProductReviewResponseDTO() {
    }
    public ProductReviewResponseDTO(ProductReview productReview) {
        this.reviewId = productReview.getReviewId();
        this.rating = productReview.getRating();
        this.reviewText = productReview.getReviewText();
        this.reviewDate = productReview.getReviewDate();
        Product product = productReview.getProduct();
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
        }
        User user = productReview.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername(); 
        }
        if (productReview.getReviewImages() != null && !productReview.getReviewImages().isEmpty()) {
            this.reviewImages = productReview.getReviewImages().stream()
                    .map(ReviewImageResponseDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.reviewImages = List.of(); 
        }
    }
    public Integer getReviewId() {
        return reviewId;
    }
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    public List<ReviewImageResponseDTO> getReviewImages() {
        return reviewImages;
    }
    public void setReviewImages(List<ReviewImageResponseDTO> reviewImages) {
        this.reviewImages = reviewImages;
    }
}
