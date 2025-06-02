package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ReviewImage;

public class ReviewImageResponseDTO {
    private Integer imageId;
    private String imageUrl;
    public ReviewImageResponseDTO() {
    }
    public ReviewImageResponseDTO(ReviewImage reviewImage) {
        this.imageId = reviewImage.getImageId();
        this.imageUrl = reviewImage.getImageUrl();
    }
    public Integer getImageId() {
        return imageId;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
