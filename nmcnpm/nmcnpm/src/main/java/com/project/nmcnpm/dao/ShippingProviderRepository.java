package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface ShippingProviderRepository extends JpaRepository<ShippingProvider, Integer> {
    List<ShippingProvider> findByProviderNameContainingIgnoreCase(String providerName);
}