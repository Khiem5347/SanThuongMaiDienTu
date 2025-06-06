package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import jakarta.validation.Valid; 

public class ProductVariantDTO {
    @NotNull(message = "Product ID cannot be null")
    private Integer productId;
    @NotBlank(message = "Color cannot be blank")
    @Size(max = 50, message = "Color cannot exceed 50 characters")
    private String color;
    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl; 
    @Valid 
    private List<ProductSizeDTO> productSizes; 
    public ProductVariantDTO() {
    }
    public ProductVariantDTO(Integer productId, String color, String imageUrl, List<ProductSizeDTO> productSizes) {
        this.productId = productId;
        this.color = color;
        this.imageUrl = imageUrl;
        this.productSizes = productSizes;
    }
    public Integer getProductId() {
        return productId;
    }
    public String getColor() {
        return color;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public List<ProductSizeDTO> getProductSizes() {
        return productSizes;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setProductSizes(List<ProductSizeDTO> productSizes) {
        this.productSizes = productSizes;
    }
}
