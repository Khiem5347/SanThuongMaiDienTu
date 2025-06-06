package com.project.nmcnpm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ProductSizeDTO {
    @NotNull(message = "Product Variant ID cannot be null")
    private Integer productVariantId;
    @NotBlank(message = "Size cannot be blank")
    @Size(max = 50, message = "Size cannot exceed 50 characters")
    private String size;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;
    public ProductSizeDTO() {
    }
    public ProductSizeDTO(Integer productVariantId, String size, BigDecimal price, Integer quantity) {
        this.productVariantId = productVariantId;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }
    public Integer getProductVariantId() {
        return productVariantId;
    }
    public String getSize() {
        return size;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
