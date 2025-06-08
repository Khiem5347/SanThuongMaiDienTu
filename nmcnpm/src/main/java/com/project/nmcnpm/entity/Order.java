package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
// Remove unused imports if these annotations are no longer used for transient fields
// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

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

    // --- FIX START: Mark fields as @Transient if they don't exist in the DB ---
    @Transient // This field does not have a corresponding column in the 'Orders' table
    private String orderTrackingNumber;

    @Transient // This field does not have a corresponding column in the 'Orders' table
    private String status;

    @Transient // This field does not have a corresponding column in the 'Orders' table
    private Date dateCreated;

    @Transient // This field does not have a corresponding column in the 'Orders' table
    private Date lastUpdated;
    // --- FIX END ---

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrder = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private History history;

    public Order() {
    }

    // --- IMPORTANT: Update constructor to remove transient fields if they aren't passed ---
    public Order(Integer orderId, User user, Address address, BigDecimal totalAmount,
                 // Removed orderTrackingNumber, status, dateCreated, lastUpdated from constructor parameters
                 Set<ProductsInOrder> productsInOrder, Payment payment, History history) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.totalAmount = totalAmount;
        // this.orderTrackingNumber = orderTrackingNumber; // REMOVE
        // this.status = status; // REMOVE
        // this.dateCreated = dateCreated; // REMOVE
        // this.lastUpdated = lastUpdated; // REMOVE
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

    // --- You can keep getters/setters if you use these fields in Java logic,
    // but they won't interact with the DB. ---
    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }
    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    // --- End of transient field getters/setters ---


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