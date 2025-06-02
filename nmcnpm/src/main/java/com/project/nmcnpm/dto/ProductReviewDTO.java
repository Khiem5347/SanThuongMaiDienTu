package com.project.nmcnpm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ProductReviewDTO {
    @NotNull(message = "Product ID cannot be null")
    private Integer productId;
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;
    @Size(max = 1000, message = "Review text cannot exceed 1000 characters")
    private String reviewText;
    @Valid 
    private List<ReviewImageDTO> reviewImages; 
    public ProductReviewDTO() {
    }
    public ProductReviewDTO(Integer productId, Integer userId, Integer rating, String reviewText, List<ReviewImageDTO> reviewImages) {
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewImages = reviewImages;
    }
    public Integer getProductId() {
        return productId;
    }
    public Integer getUserId() {
        return userId;
    }
    public Integer getRating() {
        return rating;
    }
    public String getReviewText() {
        return reviewText;
    }
    public List<ReviewImageDTO> getReviewImages() {
        return reviewImages;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public void setReviewImages(List<ReviewImageDTO> reviewImages) {
        this.reviewImages = reviewImages;
    }
}
