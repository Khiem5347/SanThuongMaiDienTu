package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductsInCart")
public class ProductsInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_in_cart_id")
    private Integer productsInCartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop; 
    @Column(name = "color", length = 50)
    private String color;
    @Column(name = "size", length = 50)
    private String size;
    @Column(name = "product_image_url", length = 255)
    private String productImageUrl; 
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;
    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;
    public ProductsInCart() {
    }
    public ProductsInCart(Cart cart, Product product, Shop shop, String color, String size, String productImageUrl, Integer productPrice, Integer productQuantity) {
        this.cart = cart;
        this.product = product;
        this.shop = shop;
        this.color = color;
        this.size = size;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
    public Integer getProductsInCartId() {
        return productsInCartId;
    }
    public Cart getCart() {
        return cart;
    }
    public Product getProduct() {
        return product;
    }
    public Shop getShop() {
        return shop;
    }
    public String getColor() {
        return color;
    }
    public String getSize() {
        return size;
    }
    public String getProductImageUrl() {
        return productImageUrl;
    }
    public Integer getProductPrice() {
        return productPrice;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductsInCartId(Integer productsInCartId) {
        this.productsInCartId = productsInCartId;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
