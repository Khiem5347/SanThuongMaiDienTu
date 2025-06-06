package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ShippingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingServiceRepository extends JpaRepository<ShippingService, Integer> {
    @Query("SELECT ss FROM ShippingService ss JOIN FETCH ss.shippingProvider sp WHERE ss.serviceId = :serviceId")
    Optional<ShippingService> findByIdWithProvider(@Param("serviceId") Integer serviceId);
    @Query(value = "SELECT ss FROM ShippingService ss JOIN FETCH ss.shippingProvider sp",
           countQuery = "SELECT count(ss) FROM ShippingService ss")
    Page<ShippingService> findAllWithProvider(Pageable pageable);
    @Query("SELECT ss FROM ShippingService ss JOIN FETCH ss.shippingProvider sp WHERE ss.shippingProvider.providerId = :providerId")
    List<ShippingService> findByShippingProviderProviderIdWithProvider(@Param("providerId") Integer providerId);
    List<ShippingService> findByShippingProviderProviderId(Integer providerId);
}
