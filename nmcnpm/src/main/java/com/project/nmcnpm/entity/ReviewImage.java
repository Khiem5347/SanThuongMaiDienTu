package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ReviewsImage")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ProductReview productReview;
    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;
    public ReviewImage() {
    }
    public ReviewImage(Integer imageId, ProductReview productReview, String imageUrl) {
        this.imageId = imageId;
        this.productReview = productReview;
        this.imageUrl = imageUrl;
    }
    public Integer getImageId() {
        return imageId;
    }
    public ProductReview getProductReview() {
        return productReview;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}