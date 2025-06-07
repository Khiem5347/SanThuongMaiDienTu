package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ProductVariants")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Integer variantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "color", length = 50)
    private String color;
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductSize> productSizes = new HashSet<>();
    public ProductVariant() {
    }
    public ProductVariant(Integer variantId, Product product, String color, String imageUrl, Set<ProductSize> productSizes) {
        this.variantId = variantId;
        this.product = product;
        this.color = color;
        this.imageUrl = imageUrl;
        this.productSizes = productSizes;
    }
    public Integer getVariantId() {
        return variantId;
    }
    public Product getProduct() {
        return product;
    }
    public String getColor() {
        return color;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public Set<ProductSize> getProductSizes() {
        return productSizes;
    }
    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setProductSizes(Set<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }
    public void addProductSize(ProductSize productSize) {
        if (productSize != null) {
            if (this.productSizes == null) {
                this.productSizes = new HashSet<>();
            }
            this.productSizes.add(productSize);
            productSize.setProductVariant(this);
        }
    }
    public void removeProductSize(ProductSize productSize) {
        if (productSize != null && this.productSizes != null) {
            this.productSizes.remove(productSize);
            productSize.setProductVariant(null);
        }
    }
}