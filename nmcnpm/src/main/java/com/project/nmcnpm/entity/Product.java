package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;
    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;
    @Column(name = "product_star")
    private Integer productStar = 0;
    @Column(name = "product_review")
    private Integer productReview = 0;
    @Column(name = "product_sold")
    private Integer productSold = 0;
    @Column(name = "product_main_image_url", nullable = false, length = 255)
    private String productMainImageUrl;
    @Column(name = "product_min_price")
    private Integer productMinPrice; 
    @Column(name = "product_max_price")
    private Integer productMaxPrice; 
    @Column(name = "remaining_quantity") 
    private Integer remainingQuantity = 0;
    @Transient
    private Integer productStock = 0;
    @Column(name = "number_of_likes")
    private Integer numberOfLikes = 0;
    @Column(name = "num_of_product_star")
    private Integer numOfProductStar = 0;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> productImages = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductVariant> productVariants = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShippingLink> shippingLinks = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductReview> productReviews = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrder = new HashSet<>();
    public Product() {
    }
    public Product(Integer productId, Category category, Shop shop, String productName, String productDescription,
                   Integer productStar, Integer productReview, Integer productSold, String productMainImageUrl,
                   Integer productMinPrice, Integer productMaxPrice, Integer productStock, Integer numberOfLikes, Integer numOfProductStar, Integer remainingQuantity, 
                   Set<ProductImage> productImages, Set<ProductVariant> productVariants, Set<ShippingLink> shippingLinks,
                   Set<ProductReview> productReviews, Set<ProductsInOrder> productsInOrder) {
        this.productId = productId;
        this.category = category;
        this.shop = shop;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productStar = productStar;
        this.productReview = productReview;
        this.productSold = productSold;
        this.productMainImageUrl = productMainImageUrl;
        this.productMinPrice = productMinPrice;
        this.productMaxPrice = productMaxPrice;
        this.productStock = productStock;
        this.numberOfLikes = numberOfLikes;
        this.numOfProductStar = numOfProductStar;
        this.remainingQuantity = remainingQuantity;
        this.productImages = productImages;
        this.productVariants = productVariants;
        this.shippingLinks = shippingLinks;
        this.productReviews = productReviews;
        this.productsInOrder = productsInOrder;
    }
    public Integer getProductId() {
        return productId;
    }
    public Category getCategory() {
        return category;
    }
    public Shop getShop() {
        return shop;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public Integer getProductStar() {
        return productStar;
    }
    public Integer getRemainingQuantity() { 
        return remainingQuantity;
    }
    public Integer getProductReview() {
        return productReview;
    }
    public Integer getProductSold() {
        return productSold;
    }
    public String getProductMainImageUrl() {
        return productMainImageUrl;
    }
    public Integer getProductMinPrice() {
        return productMinPrice;
    }
    public Integer getProductMaxPrice() {
        return productMaxPrice;
    }
    public Integer getProductStock() { 
        return productStock;
    }
    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }
    public Integer getNumOfProductStar() {
        return numOfProductStar;
    }
    public Set<ProductImage> getProductImages() {
        return productImages;
    }
    public Set<ProductVariant> getProductVariants() {
        return productVariants;
    }
    public Set<ShippingLink> getShippingLinks() {
        return shippingLinks;
    }
    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }
    public Set<ProductsInOrder> getProductsInOrder() {
        return productsInOrder;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public void setProductStar(Integer productStar) {
        this.productStar = productStar;
    }
    public void setRemainingQuantity(Integer remainingQuantity) { 
        this.remainingQuantity = remainingQuantity;
    }
    public void setProductReview(Integer productReview) {
        this.productReview = productReview;
    }
    public void setProductSold(Integer productSold) {
        this.productSold = productSold;
    }
    public void setProductMainImageUrl(String productMainImageUrl) {
        this.productMainImageUrl = productMainImageUrl;
    }
    public void setProductMinPrice(Integer productMinPrice) {
        this.productMinPrice = productMinPrice;
    }
    public void setProductMaxPrice(Integer productMaxPrice) {
        this.productMaxPrice = productMaxPrice;
    }
    public void setProductStock(Integer productStock) { 
        this.productStock = productStock;
    }
    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
    public void setNumOfProductStar(Integer numOfProductStar) {
        this.numOfProductStar = numOfProductStar;
    }
    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }
    public void setProductVariants(Set<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }
    public void setShippingLinks(Set<ShippingLink> shippingLinks) {
        this.shippingLinks = shippingLinks;
    }
    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }
    public void setProductsInOrder(Set<ProductsInOrder> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }
    public void addProductImage(ProductImage image) {
        if (image != null) {
            if (this.productImages == null) {
                this.productImages = new HashSet<>();
            }
            this.productImages.add(image);
            image.setProduct(this);
        }
    }
    public void addProductVariant(ProductVariant variant) {
        if (variant != null) {
            if (this.productVariants == null) {
                this.productVariants = new HashSet<>();
            }
            this.productVariants.add(variant);
            variant.setProduct(this);
        }
    }
    public void addShippingLink(ShippingLink link) {
        if (link != null) {
            if (this.shippingLinks == null) {
                this.shippingLinks = new HashSet<>();
            }
            this.shippingLinks.add(link);
            link.setProduct(this);
        }
    }
    public void addProductReview(ProductReview review) {
        if (review != null) {
            if (this.productReviews == null) {
                this.productReviews = new HashSet<>();
            }
            this.productReviews.add(review);
            review.setProduct(this);
        }
    }
    public void addProductsInOrder(ProductsInOrder productsInOrder) {
        if (productsInOrder != null) {
            if (this.productsInOrder == null) {
                this.productsInOrder = new HashSet<>();
            }
            this.productsInOrder.add(productsInOrder);
            productsInOrder.setProduct(this);
        }
    }
}
