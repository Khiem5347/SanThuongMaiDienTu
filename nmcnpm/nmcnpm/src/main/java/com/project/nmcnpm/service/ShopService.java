package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShopDTO;
import com.project.nmcnpm.dto.ShopResponseDTO;
import com.project.nmcnpm.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface ShopService {
    Shop createShop(ShopDTO shopDTO);
    ShopResponseDTO getShopById(Integer shopId);
    Shop updateShop(Integer shopId, ShopDTO shopDTO);
    void deleteShop(Integer shopId);
    Page<ShopResponseDTO> getAllShops(Pageable pageable);
    ShopResponseDTO getShopByUserId(Integer userId); 
    List<ShopResponseDTO> searchShopsByName(String shopName); 
}
