package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ShippingLinkDTO;
import com.project.nmcnpm.dto.ShippingLinkResponseDTO;
import com.project.nmcnpm.entity.ShippingLink; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface ShippingLinkService {
    ShippingLinkResponseDTO createShippingLink(ShippingLinkDTO shippingLinkDTO); 
    ShippingLinkResponseDTO getShippingLinkById(Integer linkId);
    void deleteShippingLink(Integer linkId);
    Page<ShippingLinkResponseDTO> getAllShippingLinks(Pageable pageable);
    List<ShippingLinkResponseDTO> getShippingLinksByProductId(Integer productId);
    List<ShippingLinkResponseDTO> getShippingLinksByProviderId(Integer providerId);
}
