package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet; 

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;
    @Column(name = "is_default")
    private Boolean isDefault = false; 
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>(); 
    public Address() {
    }
    public Address(Integer addressId, User user, String recipientName, String phone,
                   String detailAddress, Boolean isDefault, Set<Order> orders) {
        this.addressId = addressId;
        this.user = user;
        this.recipientName = recipientName;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
        this.orders = orders;
    }
    public Integer getAddressId() {
        return addressId;
    }
    public User getUser() {
        return user;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public String getPhone() {
        return phone;
    }
    public String getDetailAddress() {
        return detailAddress;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    public Set<Order> getOrders() {
        return orders;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public void addOrder(Order order) {
        if (order != null) {
            if (this.orders == null) {
                this.orders = new HashSet<>();
            }
            this.orders.add(order);
            order.setAddress(this); 
        }
    }
}