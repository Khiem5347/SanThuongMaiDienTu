package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShippingProviderDTO;
import com.project.nmcnpm.dto.ShippingProviderResponseDTO;
import com.project.nmcnpm.entity.ShippingProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShippingProviderService {
    ShippingProvider createShippingProvider(ShippingProviderDTO shippingProviderDTO);
    ShippingProviderResponseDTO getShippingProviderById(Integer providerId);
    ShippingProvider updateShippingProvider(Integer providerId, ShippingProviderDTO shippingProviderDTO);
    void deleteShippingProvider(Integer providerId);
    Page<ShippingProviderResponseDTO> getAllShippingProviders(Pageable pageable);
    List<ShippingProviderResponseDTO> searchShippingProvidersByName(String providerName);
}
