package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "Shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Integer shopId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 
    @Column(name = "shop_name", nullable = false, length = 100)
    private String shopName;
    @Column(name = "shop_description", columnDefinition = "TEXT")
    private String shopDescription;
    @Column(name = "shop_avatar_url", length = 255)
    private String shopAvatarUrl;
    @Column(name = "shop_addr", columnDefinition = "TEXT")
    private String shopAddr;
    @Column(name = "shop_revenue")
    private Integer shopRevenue;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>(); 
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInCart> productsInCarts = new HashSet<>(); 
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrders = new HashSet<>(); 
    public Shop() {
    }
    public Shop(Integer shopId, User user, String shopName, String shopDescription, String shopAvatarUrl,
                String shopAddr, Integer shopRevenue, Set<Product> products,
                Set<ProductsInCart> productsInCarts, Set<ProductsInOrder> productsInOrders) {
        this.shopId = shopId;
        this.user = user;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.shopAvatarUrl = shopAvatarUrl;
        this.shopAddr = shopAddr;
        this.shopRevenue = shopRevenue;
        this.products = products;
        this.productsInCarts = productsInCarts;
        this.productsInOrders = productsInOrders;
    }
    public Integer getShopId() {
        return shopId;
    }
    public User getUser() { 
        return user;
    }
    public String getShopName() {
        return shopName;
    }
    public String getShopDescription() {
        return shopDescription;
    }
    public String getShopAvatarUrl() {
        return shopAvatarUrl;
    }
    public String getShopAddr() {
        return shopAddr;
    }
    public Integer getShopRevenue() {
        return shopRevenue;
    }
    public Set<Product> getProducts() {
        return products;
    }
    public Set<ProductsInCart> getProductsInCarts() {
        return productsInCarts;
    }
    public Set<ProductsInOrder> getProductsInOrders() {
        return productsInOrders;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public void setUser(User user) { 
        this.user = user;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
    public void setShopAvatarUrl(String shopAvatarUrl) {
        this.shopAvatarUrl = shopAvatarUrl;
    }
    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }
    public void setShopRevenue(Integer shopRevenue) {
        this.shopRevenue = shopRevenue;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public void setProductsInCarts(Set<ProductsInCart> productsInCarts) {
        this.productsInCarts = productsInCarts;
    }
    public void setProductsInOrders(Set<ProductsInOrder> productsInOrders) {
        this.productsInOrders = productsInOrders;
    }
    public void addProduct(Product product) {
        if (product != null) {
            if (this.products == null) {
                this.products = new HashSet<>();
            }
            this.products.add(product);
            product.setShop(this); 
        }
    }
    public void removeProduct(Product product) {
        if (product != null && this.products != null) {
            this.products.remove(product);
            product.setShop(null);
        }
    }
}