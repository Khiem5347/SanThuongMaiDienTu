package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShippingServiceRepository extends JpaRepository<ShippingService, Integer> {
    List<ShippingService> findByShippingProviderProviderId(Integer providerId);
}