package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.CartItemDTO; 
import com.project.nmcnpm.dto.CartResponseDTO;
public interface CartService {
    CartResponseDTO getOrCreateCartForUser(Integer userId);

    CartResponseDTO getCartDetailsByUserId(Integer userId);

    CartResponseDTO addProductToCart(Integer userId, CartItemDTO cartItemDTO);

    CartResponseDTO updateProductQuantityInCart(Integer userId, Integer productId, String color, String size, Integer newQuantity);

    CartResponseDTO removeProductFromCart(Integer userId, Integer productId, String color, String size);

    CartResponseDTO clearCart(Integer userId);

    void deleteCart(Integer cartId);
}