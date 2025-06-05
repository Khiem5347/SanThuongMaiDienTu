package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.CartItemDTO; 
import com.project.nmcnpm.dto.CartResponseDTO; 
import com.project.nmcnpm.entity.Cart; 

public interface CartService {
    Cart getOrCreateCartForUser(Integer userId);
    CartResponseDTO getCartDetailsByUserId(Integer userId);
    CartResponseDTO addProductToCart(Integer userId, CartItemDTO cartItemDTO);
    CartResponseDTO updateProductQuantityInCart(Integer userId, Integer productId, String color, String size, Integer newQuantity);
    CartResponseDTO removeProductFromCart(Integer userId, Integer productId, String color, String size);
    CartResponseDTO clearCart(Integer userId);
    void deleteCart(Integer cartId);
}
