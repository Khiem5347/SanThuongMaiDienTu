package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ShippingProviderDTO;
import com.project.nmcnpm.dto.ShippingProviderResponseDTO;
import com.project.nmcnpm.entity.ShippingProvider;
import com.project.nmcnpm.service.ShippingProviderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-providers")
public class ShippingProviderController {
    private final ShippingProviderService shippingProviderService;
    public ShippingProviderController(ShippingProviderService shippingProviderService) {
        this.shippingProviderService = shippingProviderService;
    }
    @PostMapping
    public ResponseEntity<ShippingProviderResponseDTO> createShippingProvider(@Valid @RequestBody ShippingProviderDTO shippingProviderDTO) { // Changed return type
        try {
            ShippingProvider createdProvider = shippingProviderService.createShippingProvider(shippingProviderDTO);
            ShippingProviderResponseDTO responseDTO = shippingProviderService.getShippingProviderById(createdProvider.getProviderId());
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Internal server error creating shipping provider: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ShippingProviderResponseDTO> getShippingProviderById(@PathVariable Integer id) {
        try {
            ShippingProviderResponseDTO provider = shippingProviderService.getShippingProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Provider not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping provider by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShippingProvider> updateShippingProvider(@PathVariable Integer id, @Valid @RequestBody ShippingProviderDTO shippingProviderDTO) {
        try {
            ShippingProvider updatedProvider = shippingProviderService.updateShippingProvider(id, shippingProviderDTO);
            return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Provider not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating shipping provider: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingProvider(@PathVariable Integer id) {
        try {
            shippingProviderService.deleteShippingProvider(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Provider not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting shipping provider: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ShippingProviderResponseDTO>> getAllShippingProviders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShippingProviderResponseDTO> providers = shippingProviderService.getAllShippingProviders(pageable);
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ShippingProviderResponseDTO>> searchShippingProvidersByName(@RequestParam String name) {
        try {
            List<ShippingProviderResponseDTO> providers = shippingProviderService.searchShippingProvidersByName(name);
            return new ResponseEntity<>(providers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Internal server error searching shipping providers: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
