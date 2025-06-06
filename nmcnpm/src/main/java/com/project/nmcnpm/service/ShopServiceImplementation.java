package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ShopRepository;
import com.project.nmcnpm.dao.UserRepository; 
import com.project.nmcnpm.dto.ShopDTO;
import com.project.nmcnpm.dto.ShopResponseDTO; // Đảm bảo import DTO này
import com.project.nmcnpm.entity.Shop;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImplementation implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository; 
    public ShopServiceImplementation(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public ShopResponseDTO createShop(ShopDTO shopDTO) { 
        User user = userRepository.findById(shopDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + shopDTO.getUserId()));

        Shop existingShopForUser = shopRepository.findByUserUserId(shopDTO.getUserId());
        if (existingShopForUser != null) {
            throw new IllegalArgumentException("User with ID " + shopDTO.getUserId() + " already owns a shop.");
        }

        Shop shop = new Shop();
        shop.setShopName(shopDTO.getShopName());
        shop.setShopDescription(shopDTO.getShopDescription());
        shop.setShopAvatarUrl(shopDTO.getShopAvatarUrl());
        shop.setShopAddr(shopDTO.getShopAddr());
        shop.setShopRevenue(shopDTO.getShopRevenue() != null ? shopDTO.getShopRevenue() : 0); 
        shop.setUser(user);
        Shop savedShop = shopRepository.save(shop); 
        return new ShopResponseDTO(savedShop); 
    }

    @Override
    @Transactional(readOnly = true)
    public ShopResponseDTO getShopById(Integer shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + shopId));
        return new ShopResponseDTO(shop); 
    }

    @Override
    @Transactional
    public ShopResponseDTO updateShop(Integer shopId, ShopDTO shopDTO) { 
        Shop existingShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + shopId));
        if (shopDTO.getShopName() != null && !shopDTO.getShopName().isEmpty()) {
            existingShop.setShopName(shopDTO.getShopName());
        }
        if (shopDTO.getShopDescription() != null) {
            existingShop.setShopDescription(shopDTO.getShopDescription());
        }
        if (shopDTO.getShopAvatarUrl() != null) {
            existingShop.setShopAvatarUrl(shopDTO.getShopAvatarUrl());
        }
        if (shopDTO.getShopAddr() != null) {
            existingShop.setShopAddr(shopDTO.getShopAddr());
        }
        if (shopDTO.getShopRevenue() != null) {
            existingShop.setShopRevenue(shopDTO.getShopRevenue());
        }
        if (shopDTO.getUserId() != null && 
            (existingShop.getUser() == null || !existingShop.getUser().getUserId().equals(shopDTO.getUserId()))) {
            
            User newUser = userRepository.findById(shopDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + shopDTO.getUserId()));

            Shop shopOwnedByNewUser = shopRepository.findByUserUserId(shopDTO.getUserId());
            if (shopOwnedByNewUser != null && !shopOwnedByNewUser.getShopId().equals(shopId)) {
                throw new IllegalArgumentException("User with ID " + shopDTO.getUserId() + " already owns another shop.");
            }
            existingShop.setUser(newUser);
        }
        Shop updatedShop = shopRepository.save(existingShop);
        return new ShopResponseDTO(updatedShop); 
    }

    @Override
    @Transactional
    public void deleteShop(Integer shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new EntityNotFoundException("Shop not found with id: " + shopId);
        }
        shopRepository.deleteById(shopId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShopResponseDTO> getAllShops(Pageable pageable) {
        Page<Shop> shopsPage = shopRepository.findAll(pageable);
        return shopsPage.map(ShopResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopResponseDTO getShopByUserId(Integer userId) {
        Shop shop = shopRepository.findByUserUserId(userId);
        if (shop == null) {
            throw new EntityNotFoundException("Shop not found for user with id: " + userId);
        }
        return new ShopResponseDTO(shop);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopResponseDTO> searchShopsByName(String shopName) {
        List<Shop> shops = shopRepository.findByShopNameContainingIgnoreCase(shopName);
        return shops.stream()
                .map(ShopResponseDTO::new)
                .collect(Collectors.toList());
    }
}