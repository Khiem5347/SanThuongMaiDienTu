package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Cart;
import com.project.nmcnpm.entity.User; 
import java.util.List;
import java.util.stream.Collectors;

public class CartResponseDTO {
    private Integer cartId;
    private Integer userId;
    private String userName; 
    private List<CartItemResponseDTO> items;
    private Integer totalItems;
    private Integer totalPrice;
    public CartResponseDTO() {
    }
    public CartResponseDTO(Cart cart) {
        this.cartId = cart.getCartId();
        User user = cart.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername(); 
        }
        if (cart.getProductsInCart() != null && !cart.getProductsInCart().isEmpty()) {
            this.items = cart.getProductsInCart().stream()
                    .map(CartItemResponseDTO::new)
                    .collect(Collectors.toList());
            this.totalItems = this.items.stream()
                    .mapToInt(CartItemResponseDTO::getProductQuantity)
                    .sum();
            this.totalPrice = this.items.stream()
                    .mapToInt(item -> item.getProductPrice() * item.getProductQuantity())
                    .sum();
        } else {
            this.items = List.of(); 
            this.totalItems = 0;
            this.totalPrice = 0;
        }
    }
    public Integer getCartId() {
        return cartId;
    }
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<CartItemResponseDTO> getItems() {
        return items;
    }
    public void setItems(List<CartItemResponseDTO> items) {
        this.items = items;
    }
    public Integer getTotalItems() {
        return totalItems;
    }
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
    public Integer getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
