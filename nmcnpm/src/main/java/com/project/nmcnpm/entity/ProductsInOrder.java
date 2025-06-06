package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal; 
@Entity
@Table(name = "ProductsInOrder")
public class ProductsInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_in_order_id")
    private Integer productsInOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "size", length = 50)
    private String size;

    @Column(name = "product_image_url", nullable = false, length = 255)
    private String productImageUrl;
    @Column(name = "product_price", nullable = false)
    private Integer productPrice; 
    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;
    @Column(name = "provider_id", nullable = false) 
    private Integer providerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    public ProductsInOrder() {
    }
    public ProductsInOrder(Order order, Shop shop, String productName, String color, String size,
                           String productImageUrl, Integer productPrice, Integer productQuantity, Integer providerId) { // Đã đổi productPrice
        this.order = order;
        this.shop = shop;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice; 
        this.productQuantity = productQuantity;
        this.providerId = providerId; 
    }
    public Integer getProductsInOrderId() {
        return productsInOrderId;
    }

    public Order getOrder() {
        return order;
    }
    public Shop getShop() {
        return shop;
    }

    public String getProductName() {
        return productName;
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

    public Voucher getVoucher() {
        return voucher;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public void setProductsInOrderId(Integer productsInOrderId) {
        this.productsInOrderId = productsInOrderId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}