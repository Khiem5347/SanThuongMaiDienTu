package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewImageDTO {
    @NotBlank(message = "Image URL cannot be blank")
    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl;
    public ReviewImageDTO() {
    }
    public ReviewImageDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
