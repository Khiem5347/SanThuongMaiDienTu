package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShippingServiceDTO;
import com.project.nmcnpm.dto.ShippingServiceResponseDTO;
import com.project.nmcnpm.entity.ShippingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ShippingServiceService {
    ShippingService createShippingService(ShippingServiceDTO shippingServiceDTO);
    ShippingServiceResponseDTO getShippingServiceById(Integer serviceId);
    ShippingService updateShippingService(Integer serviceId, ShippingServiceDTO shippingServiceDTO);
    void deleteShippingService(Integer serviceId);
    Page<ShippingServiceResponseDTO> getAllShippingServices(Pageable pageable);
    List<ShippingServiceResponseDTO> getShippingServicesByProviderId(Integer providerId);
}
