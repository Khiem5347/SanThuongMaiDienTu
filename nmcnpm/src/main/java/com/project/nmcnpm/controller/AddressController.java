package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.AddressDTO;
import com.project.nmcnpm.dto.AddressResponseDTO;
import com.project.nmcnpm.service.AddressService;
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
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        try {
            AddressResponseDTO createdAddress = addressService.createAddress(addressDTO);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Internal server error creating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Integer id) {
        try {
            AddressResponseDTO address = addressService.getAddressById(id);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Address not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting address by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Integer id, @Valid @RequestBody AddressDTO addressDTO) {
        try {
            AddressResponseDTO updatedAddress = addressService.updateAddress(id, addressDTO);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Address or user not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error updating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error updating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        try {
            addressService.deleteAddress(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Address not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Deletion not allowed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        } catch (Exception e) {
            System.err.println("Internal server error deleting address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAllAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AddressResponseDTO> addresses = addressService.getAllAddresses(pageable);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUserId(@PathVariable Integer userId) {
        try {
            List<AddressResponseDTO> addresses = addressService.getAddressesByUserId(userId);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User not found when fetching addresses: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting addresses by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<AddressResponseDTO> getDefaultAddressByUserId(@PathVariable Integer userId) {
        try {
            AddressResponseDTO address = addressService.getDefaultAddressByUserId(userId);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Default address not found for user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting default address by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/user/{userId}/default/{addressId}")
    public ResponseEntity<AddressResponseDTO> setDefaultAddress(
            @PathVariable Integer userId,
            @PathVariable Integer addressId) {
        try {
            AddressResponseDTO updatedAddress = addressService.setDefaultAddress(userId, addressId);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User or Address not found for setting default: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting default address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error setting default address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}