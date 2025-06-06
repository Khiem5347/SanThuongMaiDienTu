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
    public ShippingServiceResponseDTO createShippingService(ShippingServiceDTO shippingServiceDTO) { // Changed return type to DTO
        ShippingProvider provider = shippingProviderRepository.findById(shippingServiceDTO.getProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + shippingServiceDTO.getProviderId()));

        ShippingService shippingService = new ShippingService();
        shippingService.setShippingProvider(provider);
        shippingService.setFastPrice(shippingServiceDTO.getFastPrice());
        shippingService.setDefaultPrice(shippingServiceDTO.getDefaultPrice());
        shippingService.setAddDistance(shippingServiceDTO.getAddDistance());
        provider.addShippingService(shippingService); 
        ShippingService savedService = shippingServiceRepository.save(shippingService);
        return getShippingServiceById(savedService.getServiceId());
    }

    @Override
    @Transactional(readOnly = true)
    public ShippingServiceResponseDTO getShippingServiceById(Integer serviceId) {
        ShippingService shippingService = shippingServiceRepository.findByIdWithProvider(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Service not found with id: " + serviceId));
        return new ShippingServiceResponseDTO(shippingService);
    }
    @Override
    @Transactional
    public ShippingServiceResponseDTO updateShippingService(Integer serviceId, ShippingServiceDTO shippingServiceDTO) { // Changed return type to DTO
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

            ShippingProvider oldProvider = existingService.getShippingProvider();
            ShippingProvider newProvider = shippingProviderRepository.findById(shippingServiceDTO.getProviderId())
                    .orElseThrow(() -> new EntityNotFoundException("New Shipping Provider not found with id: " + shippingServiceDTO.getProviderId()));
            oldProvider.removeShippingService(existingService);
            existingService.setShippingProvider(newProvider);
            newProvider.addShippingService(existingService);
        }
        ShippingService updatedService = shippingServiceRepository.save(existingService);
        return getShippingServiceById(updatedService.getServiceId());
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
        Page<ShippingService> servicesPage = shippingServiceRepository.findAllWithProvider(pageable);
        return servicesPage.map(ShippingServiceResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingServiceResponseDTO> getShippingServicesByProviderId(Integer providerId) {
        if (!shippingProviderRepository.existsById(providerId)) {
            throw new EntityNotFoundException("Shipping Provider not found with id: " + providerId);
        }
        List<ShippingService> services = shippingServiceRepository.findByShippingProviderProviderIdWithProvider(providerId);
        return services.stream()
                .map(ShippingServiceResponseDTO::new)
                .collect(Collectors.toList());
    }
}
