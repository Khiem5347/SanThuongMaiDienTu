package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingLinkRepository extends JpaRepository<ShippingLink, Integer> {
    @Query("SELECT sl FROM ShippingLink sl JOIN FETCH sl.shippingProvider sp JOIN FETCH sl.product p WHERE sl.linkId = :linkId")
    Optional<ShippingLink> findByIdWithProviderAndProduct(@Param("linkId") Integer linkId);
    @Query(value = "SELECT sl FROM ShippingLink sl JOIN FETCH sl.shippingProvider sp JOIN FETCH sl.product p",
           countQuery = "SELECT count(sl) FROM ShippingLink sl")
    Page<ShippingLink> findAllWithProviderAndProduct(Pageable pageable);
    @Query("SELECT sl FROM ShippingLink sl JOIN FETCH sl.shippingProvider sp JOIN FETCH sl.product p WHERE sl.product.productId = :productId")
    List<ShippingLink> findByProductProductIdWithProviderAndProduct(@Param("productId") Integer productId);
    @Query("SELECT sl FROM ShippingLink sl JOIN FETCH sl.shippingProvider sp JOIN FETCH sl.product p WHERE sl.shippingProvider.providerId = :providerId")
    List<ShippingLink> findByShippingProviderProviderIdWithProviderAndProduct(@Param("providerId") Integer providerId);
    List<ShippingLink> findByProductProductId(Integer productId);
    List<ShippingLink> findByShippingProviderProviderId(Integer providerId);
}
