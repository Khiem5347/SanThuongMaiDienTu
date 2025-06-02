package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ShippingLinkRepository;
import com.project.nmcnpm.dao.ShippingProviderRepository;
import com.project.nmcnpm.dao.ProductRepository;          
import com.project.nmcnpm.dto.ShippingLinkDTO;
import com.project.nmcnpm.dto.ShippingLinkResponseDTO;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ShippingLink;
import com.project.nmcnpm.entity.ShippingProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingLinkServiceImplementation implements ShippingLinkService {
    private final ShippingLinkRepository shippingLinkRepository;
    private final ShippingProviderRepository shippingProviderRepository;
    private final ProductRepository productRepository;
    public ShippingLinkServiceImplementation(ShippingLinkRepository shippingLinkRepository,
                                             ShippingProviderRepository shippingProviderRepository,
                                             ProductRepository productRepository) {
        this.shippingLinkRepository = shippingLinkRepository;
        this.shippingProviderRepository = shippingProviderRepository;
        this.productRepository = productRepository;
    }
    @Override
    @Transactional
    public ShippingLink createShippingLink(ShippingLinkDTO shippingLinkDTO) {
        ShippingProvider provider = shippingProviderRepository.findById(shippingLinkDTO.getProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found with id: " + shippingLinkDTO.getProviderId()));
        Product product = productRepository.findById(shippingLinkDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + shippingLinkDTO.getProductId()));
        ShippingLink shippingLink = new ShippingLink();
        shippingLink.setShippingProvider(provider);
        shippingLink.setProduct(product);
        provider.addShippingLink(shippingLink); 
        return shippingLinkRepository.save(shippingLink);
    }
    @Override
    @Transactional(readOnly = true)
    public ShippingLinkResponseDTO getShippingLinkById(Integer linkId) {
        ShippingLink shippingLink = shippingLinkRepository.findById(linkId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Link not found with id: " + linkId));
        return new ShippingLinkResponseDTO(shippingLink);
    }
    @Override
    @Transactional
    public void deleteShippingLink(Integer linkId) {
        ShippingLink linkToDelete = shippingLinkRepository.findById(linkId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping Link not found with id: " + linkId));
        if (linkToDelete.getShippingProvider() != null) {
            linkToDelete.getShippingProvider().removeShippingLink(linkToDelete);
        }
        shippingLinkRepository.delete(linkToDelete);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ShippingLinkResponseDTO> getAllShippingLinks(Pageable pageable) {
        Page<ShippingLink> linksPage = shippingLinkRepository.findAll(pageable);
        return linksPage.map(ShippingLinkResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ShippingLinkResponseDTO> getShippingLinksByProductId(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        List<ShippingLink> links = shippingLinkRepository.findByProductProductId(productId);
        return links.stream()
                .map(ShippingLinkResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<ShippingLinkResponseDTO> getShippingLinksByProviderId(Integer providerId) {
        if (!shippingProviderRepository.existsById(providerId)) {
            throw new EntityNotFoundException("Shipping Provider not found with id: " + providerId);
        }
        List<ShippingLink> links = shippingLinkRepository.findByShippingProviderProviderId(providerId);
        return links.stream()
                .map(ShippingLinkResponseDTO::new)
                .collect(Collectors.toList());
    }
}
