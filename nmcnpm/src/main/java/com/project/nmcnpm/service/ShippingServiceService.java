package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShippingServiceDTO;
import com.project.nmcnpm.dto.ShippingServiceResponseDTO;
import com.project.nmcnpm.entity.ShippingService; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface ShippingServiceService {
    ShippingServiceResponseDTO createShippingService(ShippingServiceDTO shippingServiceDTO); 
    ShippingServiceResponseDTO getShippingServiceById(Integer serviceId);
    ShippingServiceResponseDTO updateShippingService(Integer serviceId, ShippingServiceDTO shippingServiceDTO); // Changed return type to ShippingServiceResponseDTO

    void deleteShippingService(Integer serviceId);
    Page<ShippingServiceResponseDTO> getAllShippingServices(Pageable pageable);
    List<ShippingServiceResponseDTO> getShippingServicesByProviderId(Integer providerId);
}
