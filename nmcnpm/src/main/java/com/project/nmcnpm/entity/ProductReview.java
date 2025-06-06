package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "ProductReviews")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;
    @Column(name = "review_date")
    @CreationTimestamp
    private Date reviewDate;
    @OneToMany(mappedBy = "productReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReviewImage> reviewImages = new HashSet<>();
    public ProductReview() {
    }
    public ProductReview(Integer reviewId, Product product, User user, Integer rating, String reviewText, Date reviewDate, Set<ReviewImage> reviewImages) {
        this.reviewId = reviewId;
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.reviewImages = reviewImages;
    }
    public Integer getReviewId() {
        return reviewId;
    }
    public Product getProduct() {
        return product;
    }
    public User getUser() {
        return user;
    }
    public Integer getRating() {
        return rating;
    }
    public String getReviewText() {
        return reviewText;
    }
    public Date getReviewDate() {
        return reviewDate;
    }
    public Set<ReviewImage> getReviewImages() {
        return reviewImages;
    }
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    public void setReviewImages(Set<ReviewImage> reviewImages) {
        this.reviewImages = reviewImages;
    }
    public void addReviewImage(ReviewImage image) {
        if (image != null) {
            if (this.reviewImages == null) {
                this.reviewImages = new HashSet<>();
            }
            this.reviewImages.add(image);
            image.setProductReview(this); 
        }
    }
    public void removeReviewImage(ReviewImage image) {
        if (image != null && this.reviewImages != null) {
            this.reviewImages.remove(image);
            image.setProductReview(null);
        }
    }
}