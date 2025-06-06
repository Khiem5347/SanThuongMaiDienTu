package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.sql.Date; 
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    @Column(name = "full_name", length = 100)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "date_of_birth")
    private Date dateOfBirth; 
    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "user_role", columnDefinition = "SET('buyer', 'seller', 'admin') DEFAULT 'buyer'")
    private String userRole; 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Shop> shops = new HashSet<>(); 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>(); 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>(); 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>(); 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductReview> productReviews = new HashSet<>(); 
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Conversation> initiatedConversations = new HashSet<>(); 
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Conversation> receivedConversations = new HashSet<>();
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<History> histories = new HashSet<>(); 
    public enum Gender {
        male, female
    }
    public User() {
    }
    public User(Integer userId, String username, String passwordHash, String email, String fullName,
                Gender gender, Date dateOfBirth, String avatarUrl, String phone, String userRole,
                Set<Shop> shops, Set<Cart> carts, Set<Address> addresses, Set<Order> orders,
                Set<ProductReview> productReviews, Set<Conversation> initiatedConversations,
                Set<Conversation> receivedConversations, Set<Message> sentMessages, Set<History> histories) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.userRole = userRole;
        this.shops = shops;
        this.carts = carts;
        this.addresses = addresses;
        this.orders = orders;
        this.productReviews = productReviews;
        this.initiatedConversations = initiatedConversations;
        this.receivedConversations = receivedConversations;
        this.sentMessages = sentMessages;
        this.histories = histories;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public Gender getGender() {
        return gender;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getPhone() {
        return phone;
    }
    public String getUserRole() {
        return userRole;
    }
    public Set<Shop> getShops() {
        return shops;
    }
    public Set<Cart> getCarts() {
        return carts;
    }
    public Set<Address> getAddresses() {
        return addresses;
    }
    public Set<Order> getOrders() {
        return orders;
    }
    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }
    public Set<Conversation> getInitiatedConversations() {
        return initiatedConversations;
    }
    public Set<Conversation> getReceivedConversations() {
        return receivedConversations;
    }
    public Set<Message> getSentMessages() {
        return sentMessages;
    }
    public Set<History> getHistories() {
        return histories;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }
    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }
    public void setInitiatedConversations(Set<Conversation> initiatedConversations) {
        this.initiatedConversations = initiatedConversations;
    }
    public void setReceivedConversations(Set<Conversation> receivedConversations) {
        this.receivedConversations = receivedConversations;
    }
    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }
    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }
    public void addShop(Shop shop) {
        if (shop != null) {
            if (this.shops == null) this.shops = new HashSet<>();
            this.shops.add(shop);
            shop.setUser(this);
        }
    }
    public void addCart(Cart cart) {
        if (cart != null) {
            if (this.carts == null) this.carts = new HashSet<>();
            this.carts.add(cart);
            cart.setUser(this);
        }
    }
    public void addAddress(Address address) {
        if (address != null) {
            if (this.addresses == null) this.addresses = new HashSet<>();
            this.addresses.add(address);
            address.setUser(this);
        }
    }
    public void addOrder(Order order) {
        if (order != null) {
            if (this.orders == null) this.orders = new HashSet<>();
            this.orders.add(order);
            order.setUser(this);
        }
    }
    public void addProductReview(ProductReview review) {
        if (review != null) {
            if (this.productReviews == null) this.productReviews = new HashSet<>();
            this.productReviews.add(review);
            review.setUser(this);
        }
    }
    public void addInitiatedConversation(Conversation conversation) {
        if (conversation != null) {
            if (this.initiatedConversations == null) this.initiatedConversations = new HashSet<>();
            this.initiatedConversations.add(conversation);
            conversation.setBuyer(this);
        }
    }
    public void addReceivedConversation(Conversation conversation) {
        if (conversation != null) {
            if (this.receivedConversations == null) this.receivedConversations = new HashSet<>();
            this.receivedConversations.add(conversation);
            conversation.setSeller(this);
        }
    }
    public void addSentMessage(Message message) {
        if (message != null) {
            if (this.sentMessages == null) this.sentMessages = new HashSet<>();
            this.sentMessages.add(message);
            message.setSender(this);
        }
    }
    public void addHistory(History history) {
        if (history != null) {
            if (this.histories == null) this.histories = new HashSet<>();
            this.histories.add(history);
            history.setUser(this);
        }
    }
}