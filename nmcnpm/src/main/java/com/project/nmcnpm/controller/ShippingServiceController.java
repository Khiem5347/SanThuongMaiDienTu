package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ShippingServiceDTO;
import com.project.nmcnpm.dto.ShippingServiceResponseDTO;
import com.project.nmcnpm.entity.ShippingService;
import com.project.nmcnpm.service.ShippingServiceService;
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
@RequestMapping("/api/shipping-services")
public class ShippingServiceController {
    private final ShippingServiceService shippingServiceService;
    public ShippingServiceController(ShippingServiceService shippingServiceService) {
        this.shippingServiceService = shippingServiceService;
    }
    @PostMapping
    public ResponseEntity<ShippingService> createShippingService(@Valid @RequestBody ShippingServiceDTO shippingServiceDTO) {
        try {
            ShippingService createdService = shippingServiceService.createShippingService(shippingServiceDTO);
            return new ResponseEntity<>(createdService, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating shipping service: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Internal server error creating shipping service: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ShippingServiceResponseDTO> getShippingServiceById(@PathVariable Integer id) {
        try {
            ShippingServiceResponseDTO service = shippingServiceService.getShippingServiceById(id);
            return new ResponseEntity<>(service, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Service not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping service by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShippingService> updateShippingService(@PathVariable Integer id, @Valid @RequestBody ShippingServiceDTO shippingServiceDTO) {
        try {
            ShippingService updatedService = shippingServiceService.updateShippingService(id, shippingServiceDTO);
            return new ResponseEntity<>(updatedService, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Service or provider not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating shipping service: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingService(@PathVariable Integer id) {
        try {
            shippingServiceService.deleteShippingService(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Service not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting shipping service: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ShippingServiceResponseDTO>> getAllShippingServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShippingServiceResponseDTO> services = shippingServiceService.getAllShippingServices(pageable);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ShippingServiceResponseDTO>> getShippingServicesByProviderId(@PathVariable Integer providerId) {
        try {
            List<ShippingServiceResponseDTO> services = shippingServiceService.getShippingServicesByProviderId(providerId);
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Provider not found when fetching services: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping services by provider ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
