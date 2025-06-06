package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrder = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private History history;
    public Order() {
    }

    public Order(Integer orderId, User user, Address address, BigDecimal totalAmount,
                 Set<ProductsInOrder> productsInOrder, Payment payment, History history) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.totalAmount = totalAmount;
        this.productsInOrder = productsInOrder;
        this.payment = payment;
        this.history = history;
    }
    public Order(User user, Address address, BigDecimal totalAmount) {
        this.user = user;
        this.address = address;
        this.totalAmount = totalAmount;
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

    public void setProductsInOrder(Set<ProductsInOrder> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public void setPayment(Payment payment) {
        if (this.payment != null && !this.payment.equals(payment)) {
            this.payment.setOrder(null);
        }
        this.payment = payment;
        if (payment != null && !this.equals(payment.getOrder())) {
            payment.setOrder(this);
        }
    }

    public void setHistory(History history) {
        if (this.history != null && !this.history.equals(history)) {
            this.history.setOrder(null);
        }
        this.history = history;
        if (history != null && !this.equals(history.getOrder())) {
            history.setOrder(this);
        }
    }
    public void addProductInOrder(ProductsInOrder item) {
        if (productsInOrder == null) {
            productsInOrder = new HashSet<>();
        }
        productsInOrder.add(item);
        item.setOrder(this); 
    }
    public void removeProductInOrder(ProductsInOrder item) {
        if (productsInOrder != null) {
            productsInOrder.remove(item);
            item.setOrder(null); 
        }
    }
}