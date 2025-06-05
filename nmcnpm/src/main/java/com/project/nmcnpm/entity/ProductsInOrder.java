package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductsInOrder")
public class ProductsInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_in_order_id")
    private Integer productsInOrderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop; 
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName; 
    @Column(name = "color", length = 50)
    private String color;
    @Column(name = "size", length = 50)
    private String size;
    @Column(name = "product_image_url", length = 255)
    private String productImageUrl; 
    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice; 
    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id") 
    private Voucher voucher;
    public ProductsInOrder() {
    }
    public ProductsInOrder(Order order, Product product, Shop shop, String productName, String color, String size, String productImageUrl, BigDecimal productPrice, Integer productQuantity) {
        this.order = order;
        this.product = product;
        this.shop = shop;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
    public Integer getProductsInOrderId() {
        return productsInOrderId;
    }
    public Order getOrder() {
      return order;
    }
    public Product getProduct() {
        return product;
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
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public Voucher getVoucher() {
        return voucher;
    }
    public void setProductsInOrderId(Integer productsInOrderId) {
        this.productsInOrderId = productsInOrderId;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
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
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
