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
    public ShippingProvider createShippingProvider(ShippingProviderDTO shippingProviderDTO) {
        ShippingProvider shippingProvider = new ShippingProvider();
        shippingProvider.setProviderName(shippingProviderDTO.getProviderName());
        shippingProvider.setContactPhone(shippingProviderDTO.getContactPhone());
        return shippingProviderRepository.save(shippingProvider);
    }
    @Override
    @Transactional(readOnly = true)
    public ShippingProviderResponseDTO getShippingProviderById(Integer providerId) {
        ShippingProvider shippingProvider = shippingProviderRepository.findById(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + providerId));
        return new ShippingProviderResponseDTO(shippingProvider);
    }
    @Override
    @Transactional
    public ShippingProvider updateShippingProvider(Integer providerId, ShippingProviderDTO shippingProviderDTO) {
        ShippingProvider existingProvider = shippingProviderRepository.findById(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + providerId));

        if (shippingProviderDTO.getProviderName() != null && !shippingProviderDTO.getProviderName().isEmpty()) {
            existingProvider.setProviderName(shippingProviderDTO.getProviderName());
        }
        if (shippingProviderDTO.getContactPhone() != null) {
            existingProvider.setContactPhone(shippingProviderDTO.getContactPhone());
        }
        return shippingProviderRepository.save(existingProvider);
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
        Page<ShippingProvider> providersPage = shippingProviderRepository.findAll(pageable);
        return providersPage.map(ShippingProviderResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ShippingProviderResponseDTO> searchShippingProvidersByName(String providerName) {
        List<ShippingProvider> providers = shippingProviderRepository.findByProviderNameContainingIgnoreCase(providerName);
        return providers.stream()
                .map(ShippingProviderResponseDTO::new)
                .collect(Collectors.toList());
    }
}
