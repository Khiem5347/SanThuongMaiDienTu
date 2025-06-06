package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ShippingProviderRepository;
import com.project.nmcnpm.dto.ShippingProviderDTO;
import com.project.nmcnpm.dto.ShippingProviderResponseDTO;
import com.project.nmcnpm.entity.ShippingProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingProviderServiceImplementation implements ShippingProviderService {
    private final ShippingProviderRepository shippingProviderRepository;

    public ShippingProviderServiceImplementation(ShippingProviderRepository shippingProviderRepository) {
        this.shippingProviderRepository = shippingProviderRepository;
    }

    @Override
    @Transactional
    public ShippingProviderResponseDTO createShippingProvider(ShippingProviderDTO shippingProviderDTO) { // Changed return type to DTO
        ShippingProvider shippingProvider = new ShippingProvider();
        shippingProvider.setProviderName(shippingProviderDTO.getProviderName());
        shippingProvider.setContactPhone(shippingProviderDTO.getContactPhone());
        ShippingProvider savedProvider = shippingProviderRepository.save(shippingProvider);
        return getShippingProviderById(savedProvider.getProviderId());
    }

    @Override
    @Transactional(readOnly = true)
    public ShippingProviderResponseDTO getShippingProviderById(Integer providerId) {
        ShippingProvider shippingProvider = shippingProviderRepository.findByIdWithLinks(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + providerId));
        return new ShippingProviderResponseDTO(shippingProvider);
    }

    @Override
    @Transactional
    public ShippingProviderResponseDTO updateShippingProvider(Integer providerId, ShippingProviderDTO shippingProviderDTO) { // Changed return type to DTO
        ShippingProvider existingProvider = shippingProviderRepository.findById(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + providerId));

        if (shippingProviderDTO.getProviderName() != null && !shippingProviderDTO.getProviderName().isEmpty()) {
            existingProvider.setProviderName(shippingProviderDTO.getProviderName());
        }
        if (shippingProviderDTO.getContactPhone() != null) {
            existingProvider.setContactPhone(shippingProviderDTO.getContactPhone());
        }

        ShippingProvider updatedProvider = shippingProviderRepository.save(existingProvider);
        return getShippingProviderById(updatedProvider.getProviderId());
    }

    @Override
    @Transactional
    public void deleteShippingProvider(Integer providerId) {
        if (!shippingProviderRepository.existsById(providerId)) {
            throw new EntityNotFoundException("Shipping Provider not found with id: " + providerId);
        }
        shippingProviderRepository.deleteById(providerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShippingProviderResponseDTO> getAllShippingProviders(Pageable pageable) {
        Page<ShippingProvider> providersPage = shippingProviderRepository.findAllWithLinks(pageable);
        return providersPage.map(ShippingProviderResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingProviderResponseDTO> searchShippingProvidersByName(String providerName) {
        List<ShippingProvider> providers = shippingProviderRepository.findByProviderNameContainingIgnoreCaseWithLinks(providerName);
        return providers.stream()
                .map(ShippingProviderResponseDTO::new)
                .collect(Collectors.toList());
    }
}
