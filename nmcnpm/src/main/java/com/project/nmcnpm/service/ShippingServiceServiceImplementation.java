package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ShippingServiceRepository;
import com.project.nmcnpm.dao.ShippingProviderRepository; 
import com.project.nmcnpm.dto.ShippingServiceDTO;
import com.project.nmcnpm.dto.ShippingServiceResponseDTO;
import com.project.nmcnpm.entity.ShippingProvider;
import com.project.nmcnpm.entity.ShippingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingServiceServiceImplementation implements ShippingServiceService {
    private final ShippingServiceRepository shippingServiceRepository;
    private final ShippingProviderRepository shippingProviderRepository;
    public ShippingServiceServiceImplementation(ShippingServiceRepository shippingServiceRepository,
                                                ShippingProviderRepository shippingProviderRepository) {
        this.shippingServiceRepository = shippingServiceRepository;
        this.shippingProviderRepository = shippingProviderRepository;
    }
    @Override
    @Transactional
    public ShippingService createShippingService(ShippingServiceDTO shippingServiceDTO) {
        ShippingProvider provider = shippingProviderRepository.findById(shippingServiceDTO.getProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + shippingServiceDTO.getProviderId()));
        ShippingService shippingService = new ShippingService();
        shippingService.setShippingProvider(provider);
        shippingService.setFastPrice(shippingServiceDTO.getFastPrice());
        shippingService.setDefaultPrice(shippingServiceDTO.getDefaultPrice());
        shippingService.setAddDistance(shippingServiceDTO.getAddDistance());
        provider.addShippingService(shippingService); 
        return shippingServiceRepository.save(shippingService);
    }
    @Override
    @Transactional(readOnly = true)
    public ShippingServiceResponseDTO getShippingServiceById(Integer serviceId) {
        ShippingService shippingService = shippingServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Service not found with id: " + serviceId));
        return new ShippingServiceResponseDTO(shippingService);
    }
    @Override
    @Transactional
    public ShippingService updateShippingService(Integer serviceId, ShippingServiceDTO shippingServiceDTO) {
        ShippingService existingService = shippingServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Service not found with id: " + serviceId));
        if (shippingServiceDTO.getFastPrice() != null) {
            existingService.setFastPrice(shippingServiceDTO.getFastPrice());
        }
        if (shippingServiceDTO.getDefaultPrice() != null) {
            existingService.setDefaultPrice(shippingServiceDTO.getDefaultPrice());
        }
        if (shippingServiceDTO.getAddDistance() != null) {
            existingService.setAddDistance(shippingServiceDTO.getAddDistance());
        }
        if (shippingServiceDTO.getProviderId() != null &&
            !existingService.getShippingProvider().getProviderId().equals(shippingServiceDTO.getProviderId())) {
            ShippingProvider newProvider = shippingProviderRepository.findById(shippingServiceDTO.getProviderId())
                    .orElseThrow(() -> new EntityNotFoundException("New Shipping Provider not found with id: " + shippingServiceDTO.getProviderId()));
            existingService.getShippingProvider().removeShippingService(existingService);
            existingService.setShippingProvider(newProvider);
            newProvider.addShippingService(existingService);
        }
        return shippingServiceRepository.save(existingService);
    }
    @Override
    @Transactional
    public void deleteShippingService(Integer serviceId) {
        ShippingService serviceToDelete = shippingServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Service not found with id: " + serviceId));
        if (serviceToDelete.getShippingProvider() != null) {
            serviceToDelete.getShippingProvider().removeShippingService(serviceToDelete);
        }
        shippingServiceRepository.delete(serviceToDelete);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ShippingServiceResponseDTO> getAllShippingServices(Pageable pageable) {
        Page<ShippingService> servicesPage = shippingServiceRepository.findAll(pageable);
        return servicesPage.map(ShippingServiceResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ShippingServiceResponseDTO> getShippingServicesByProviderId(Integer providerId) {
        if (!shippingProviderRepository.existsById(providerId)) {
            throw new EntityNotFoundException("Shipping Provider not found with id: " + providerId);
        }
        List<ShippingService> services = shippingServiceRepository.findByShippingProviderProviderId(providerId);
        return services.stream()
                .map(ShippingServiceResponseDTO::new)
                .collect(Collectors.toList());
    }
}
