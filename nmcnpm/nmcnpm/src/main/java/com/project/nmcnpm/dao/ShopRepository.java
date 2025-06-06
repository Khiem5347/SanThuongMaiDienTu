package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop findByUserUserId(Integer userId);
    List<Shop> findByShopNameContainingIgnoreCase(String shopName);
}