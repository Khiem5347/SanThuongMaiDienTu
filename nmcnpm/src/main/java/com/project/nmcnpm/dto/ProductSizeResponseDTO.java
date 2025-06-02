package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ProductSize;
import com.project.nmcnpm.entity.ProductVariant; 
import java.math.BigDecimal;

public class ProductSizeResponseDTO {
    private Integer sizeId;
    private Integer productVariantId;
    private String productVariantColor; 
    private String size;
    private BigDecimal price;
    private Integer quantity;
    public ProductSizeResponseDTO() {
    }
    public ProductSizeResponseDTO(ProductSize productSize) {
        this.sizeId = productSize.getSizeId();
        this.size = productSize.getSize();
        this.price = productSize.getPrice();
        this.quantity = productSize.getQuantity();
        ProductVariant variant = productSize.getProductVariant();
        if (variant != null) {
            this.productVariantId = variant.getVariantId();
            this.productVariantColor = variant.getColor(); 
        }
    }
    public Integer getSizeId() {
        return sizeId;
    }
    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }
    public Integer getProductVariantId() {
        return productVariantId;
    }
    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }
    public String getProductVariantColor() {
        return productVariantColor;
    }
    public void setProductVariantColor(String productVariantColor) {
        this.productVariantColor = productVariantColor;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
