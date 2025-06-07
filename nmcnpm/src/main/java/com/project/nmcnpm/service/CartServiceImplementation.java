package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.CartRepository;
import com.project.nmcnpm.dao.ProductsInCartRepository;
import com.project.nmcnpm.dao.ProductRepository; 
import com.project.nmcnpm.dao.ShopRepository;     
import com.project.nmcnpm.dao.UserRepository;   
import com.project.nmcnpm.dto.CartItemDTO;
import com.project.nmcnpm.dto.CartResponseDTO;
import com.project.nmcnpm.entity.Cart;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ProductsInCart;
import com.project.nmcnpm.entity.Shop;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.Optional;

@Service
public class CartServiceImplementation implements CartService {
    private final CartRepository cartRepository;
    private final ProductsInCartRepository productsInCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    public CartServiceImplementation(CartRepository cartRepository,
                                     ProductsInCartRepository productsInCartRepository,
                                     UserRepository userRepository,
                                     ProductRepository productRepository,
                                     ShopRepository shopRepository) {
        this.cartRepository = cartRepository;
        this.productsInCartRepository = productsInCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }
    @Override
    @Transactional
    public Cart getOrCreateCartForUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User user = userOptional.get();
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }
    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartDetailsByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            return new CartResponseDTO();
        }
        return new CartResponseDTO(cart);
    }
    @Override
    @Transactional
    public CartResponseDTO addProductToCart(Integer userId, CartItemDTO cartItemDTO) {
        Cart cart = getOrCreateCartForUser(userId); 
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + cartItemDTO.getProductId()));
        Shop shop = shopRepository.findById(cartItemDTO.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + cartItemDTO.getShopId()));
        Optional<ProductsInCart> existingItemOptional = productsInCartRepository
                .findByCartCartIdAndProductProductIdAndColorAndSize(
                        cart.getCartId(),
                        cartItemDTO.getProductId(),
                        cartItemDTO.getColor(),
                        cartItemDTO.getSize()
                );
        ProductsInCart item;
        if (existingItemOptional.isPresent()) {
            item = existingItemOptional.get();
            item.setProductQuantity(item.getProductQuantity() + cartItemDTO.getProductQuantity());
            item.setProductPrice(cartItemDTO.getProductPrice());
            item.setProductImageUrl(cartItemDTO.getProductImageUrl());
        } else {
            item = new ProductsInCart();
            item.setCart(cart);
            item.setProduct(product);
            item.setShop(shop);
            item.setColor(cartItemDTO.getColor());
            item.setSize(cartItemDTO.getSize());
            item.setProductImageUrl(cartItemDTO.getProductImageUrl());
            item.setProductPrice(cartItemDTO.getProductPrice());
            item.setProductQuantity(cartItemDTO.getProductQuantity());
            cart.addProductInCart(item); 
        }
        productsInCartRepository.save(item); 
        return new CartResponseDTO(cart); 
    }
    @Override
    @Transactional
    public CartResponseDTO updateProductQuantityInCart(Integer userId, Integer productId, String color, String size, Integer newQuantity) {
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user with id: " + userId);
        }
        ProductsInCart item = productsInCartRepository
                .findByCartCartIdAndProductProductIdAndColorAndSize(cart.getCartId(), productId, color, size)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + ", Color " + color + ", Size " + size + " not found in cart."));
        if (newQuantity <= 0) {
            cart.removeProductInCart(item); 
            productsInCartRepository.delete(item); 
        } else {
            item.setProductQuantity(newQuantity);
            productsInCartRepository.save(item); 
        }
        return new CartResponseDTO(cart);
    }
    @Override
    @Transactional
    public CartResponseDTO removeProductFromCart(Integer userId, Integer productId, String color, String size) {
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user with id: " + userId);
        }
        ProductsInCart item = productsInCartRepository
                .findByCartCartIdAndProductProductIdAndColorAndSize(cart.getCartId(), productId, color, size)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + ", Color " + color + ", Size " + size + " not found in cart."));
        cart.removeProductInCart(item); 
        productsInCartRepository.delete(item); 
        return new CartResponseDTO(cart); 
    }
    @Override
    @Transactional
    public CartResponseDTO clearCart(Integer userId) {
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user with id: " + userId);
        }
        if (cart.getProductsInCart() != null) {
            productsInCartRepository.deleteAll(cart.getProductsInCart());
            cart.getProductsInCart().clear();
        }
        return new CartResponseDTO(cart); 
    }
    @Override
    @Transactional
    public void deleteCart(Integer cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException("Cart not found with id: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }
}
