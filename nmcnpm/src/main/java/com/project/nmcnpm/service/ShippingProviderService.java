package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShippingProviderDTO;
import com.project.nmcnpm.dto.ShippingProviderResponseDTO;
import com.project.nmcnpm.entity.ShippingProvider; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ShippingProviderService {
    ShippingProviderResponseDTO createShippingProvider(ShippingProviderDTO shippingProviderDTO); 
    ShippingProviderResponseDTO getShippingProviderById(Integer providerId);
    ShippingProviderResponseDTO updateShippingProvider(Integer providerId, ShippingProviderDTO shippingProviderDTO); // Changed return type to ShippingProviderResponseDTO

    void deleteShippingProvider(Integer providerId);
    Page<ShippingProviderResponseDTO> getAllShippingProviders(Pageable pageable);
    List<ShippingProviderResponseDTO> searchShippingProvidersByName(String providerName);
}
