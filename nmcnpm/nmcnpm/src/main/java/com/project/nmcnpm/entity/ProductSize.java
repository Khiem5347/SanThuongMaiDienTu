package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductSizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Integer sizeId;
    @Column(name = "size", nullable = false, length = 50)
    private String size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    public ProductSize() {
    }
    public ProductSize(Integer sizeId, String size, ProductVariant productVariant, BigDecimal price, Integer quantity) {
        this.sizeId = sizeId;
        this.size = size;
        this.productVariant = productVariant;
        this.price = price;
        this.quantity = quantity;
    }
    public Integer getSizeId() {
        return sizeId;
    }
    public String getSize() {
        return size;
    }
    public ProductVariant getProductVariant() {
        return productVariant;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}