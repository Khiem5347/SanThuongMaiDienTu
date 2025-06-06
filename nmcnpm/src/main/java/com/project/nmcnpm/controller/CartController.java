package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.CartItemDTO;
import com.project.nmcnpm.dto.CartResponseDTO;
import com.project.nmcnpm.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/user/{userId}/create-or-get")
    public ResponseEntity<CartResponseDTO> getOrCreateCartForUser(@PathVariable Integer userId) {
        try {
            CartResponseDTO cart = cartService.getOrCreateCartForUser(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK); 
        } catch (EntityNotFoundException e) {
            System.err.println("User not found when trying to get or create cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting or creating cart for user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Integer userId) {
        try {
            CartResponseDTO cart = cartService.getCartDetailsByUserId(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User or Cart not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting cart by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @PathVariable Integer userId,
            @Valid @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartResponseDTO updatedCart = cartService.addProductToCart(userId, cartItemDTO);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Internal server error adding product to cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{userId}/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateProductQuantityInCart(
            @PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam Integer newQuantity) {
        try {
            CartResponseDTO updatedCart = cartService.updateProductQuantityInCart(userId, productId, color, size, newQuantity);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Error updating product quantity in cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid quantity: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error updating product quantity in cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{userId}/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(
            @PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size) {
        try {
            CartResponseDTO updatedCart = cartService.removeProductFromCart(userId, productId, color, size);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Error removing product from cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error removing product from cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<CartResponseDTO> clearCart(@PathVariable Integer userId) {
        try {
            CartResponseDTO clearedCart = cartService.clearCart(userId);
            return new ResponseEntity<>(clearedCart, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Cart not found for clearing: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error clearing cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer cartId) {
        try {
            cartService.deleteCart(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Cart not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting cart: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}