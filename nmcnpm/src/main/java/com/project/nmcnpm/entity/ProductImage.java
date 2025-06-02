package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductsImage")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;
    @Column(name = "product_url", nullable = false, length = 255)
    private String productUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") 
    private Product product;
    public ProductImage() {
    }
    public ProductImage(Integer imageId, String productUrl, Product product) {
        this.imageId = imageId;
        this.productUrl = productUrl;
        this.product = product;
    }
    public Integer getImageId() {
        return imageId;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public Product getProduct() {
        return product;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}