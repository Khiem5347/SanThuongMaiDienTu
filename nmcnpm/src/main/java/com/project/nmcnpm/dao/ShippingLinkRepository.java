package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShippingLinkRepository extends JpaRepository<ShippingLink, Integer> {
    List<ShippingLink> findByProductProductId(Integer productId);
    List<ShippingLink> findByShippingProviderProviderId(Integer providerId);
}