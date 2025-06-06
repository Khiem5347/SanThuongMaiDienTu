package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingProviderRepository extends JpaRepository<ShippingProvider, Integer> {
    @Query("SELECT DISTINCT sp FROM ShippingProvider sp LEFT JOIN FETCH sp.shippingLinks sl WHERE sp.providerId = :providerId")
    Optional<ShippingProvider> findByIdWithLinks(@Param("providerId") Integer providerId);
    @Query(value = "SELECT DISTINCT sp FROM ShippingProvider sp LEFT JOIN FETCH sp.shippingLinks sl",
           countQuery = "SELECT count(sp) FROM ShippingProvider sp")
    Page<ShippingProvider> findAllWithLinks(Pageable pageable);
    @Query("SELECT DISTINCT sp FROM ShippingProvider sp LEFT JOIN FETCH sp.shippingLinks sl WHERE LOWER(sp.providerName) LIKE LOWER(CONCAT('%', :providerName, '%'))")
    List<ShippingProvider> findByProviderNameContainingIgnoreCaseWithLinks(@Param("providerName") String providerName);
    List<ShippingProvider> findByProviderNameContainingIgnoreCase(String providerName);
}
