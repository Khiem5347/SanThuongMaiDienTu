package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ShippingLinkDTO;
import com.project.nmcnpm.dto.ShippingLinkResponseDTO;
import com.project.nmcnpm.service.ShippingLinkService;
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
@RequestMapping("/api/shipping-links")
public class ShippingLinkController {

    private final ShippingLinkService shippingLinkService;
    public ShippingLinkController(ShippingLinkService shippingLinkService) {
        this.shippingLinkService = shippingLinkService;
    }
    @PostMapping
    public ResponseEntity<ShippingLinkResponseDTO> createShippingLink(@Valid @RequestBody ShippingLinkDTO shippingLinkDTO) { // Changed return type to ShippingLinkResponseDTO
        try {
            ShippingLinkResponseDTO createdLink = shippingLinkService.createShippingLink(shippingLinkDTO);
            return new ResponseEntity<>(createdLink, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating shipping link: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Internal server error creating shipping link: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ShippingLinkResponseDTO> getShippingLinkById(@PathVariable Integer id) {
        try {
            ShippingLinkResponseDTO link = shippingLinkService.getShippingLinkById(id);
            return new ResponseEntity<>(link, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Link not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping link by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingLink(@PathVariable Integer id) {
        try {
            shippingLinkService.deleteShippingLink(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Link not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting shipping link: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ShippingLinkResponseDTO>> getAllShippingLinks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShippingLinkResponseDTO> links = shippingLinkService.getAllShippingLinks(pageable);
        return new ResponseEntity<>(links, HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ShippingLinkResponseDTO>> getShippingLinksByProductId(@PathVariable Integer productId) {
        try {
            List<ShippingLinkResponseDTO> links = shippingLinkService.getShippingLinksByProductId(productId);
            return new ResponseEntity<>(links, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Product not found when fetching shipping links: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping links by product ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ShippingLinkResponseDTO>> getShippingLinksByProviderId(@PathVariable Integer providerId) {
        try {
            List<ShippingLinkResponseDTO> links = shippingLinkService.getShippingLinksByProviderId(providerId);
            return new ResponseEntity<>(links, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shipping Provider not found when fetching shipping links: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shipping links by provider ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
