package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInCart> productsInCart = new HashSet<>(); 
    public Cart() {
    }
    public Cart(Integer cartId, User user, Set<ProductsInCart> productsInCart) {
        this.cartId = cartId;
        this.user = user;
        this.productsInCart = productsInCart;
    }
    public Integer getCartId() {
        return cartId;
    }
    public User getUser() {
        return user;
    }
    public Set<ProductsInCart> getProductsInCart() {
        return productsInCart;
    }
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setProductsInCart(Set<ProductsInCart> productsInCart) {
        this.productsInCart = productsInCart;
    }
    public void addProductInCart(ProductsInCart product) {
        if (product != null) {
            if (this.productsInCart == null) {
                this.productsInCart = new HashSet<>();
            }
            this.productsInCart.add(product);
            product.setCart(this); 
        }
    }
    public void removeProductInCart(ProductsInCart product) {
        if (product != null && this.productsInCart != null) {
            this.productsInCart.remove(product);
            product.setCart(null);
        }
    }
}