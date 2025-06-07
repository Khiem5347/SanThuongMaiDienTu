package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @Column(name = "order_tracking_number", length = 255)
    private String orderTrackingNumber;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;
    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrder = new HashSet<>(); // Initialize here
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private History history;
    public Order() {
    }
    public Order(Integer orderId, User user, Address address, BigDecimal totalAmount, String orderTrackingNumber,
                 String status, Date dateCreated, Date lastUpdated, Set<ProductsInOrder> productsInOrder,
                 Payment payment, History history) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.totalAmount = totalAmount;
        this.orderTrackingNumber = orderTrackingNumber;
        this.status = status;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.productsInOrder = productsInOrder;
        this.payment = payment;
        this.history = history;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public User getUser() {
        return user;
    }
    public Address getAddress() {
        return address;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }
    public String getStatus() {
        return status;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public Set<ProductsInOrder> getProductsInOrder() {
        return productsInOrder;
    }
    public Payment getPayment() {
        return payment;
    }
    public History getHistory() {
        return history;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setProductsInOrder(Set<ProductsInOrder> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public void setHistory(History history) {
        this.history = history;
    }
    public void addProductInOrder(ProductsInOrder item) {
        if (productsInOrder == null) {
            productsInOrder = new HashSet<>();
        }
        productsInOrder.add(item);
        item.setOrder(this);
    }
}