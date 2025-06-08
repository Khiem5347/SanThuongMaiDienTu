package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.AddressDTO;
import com.project.nmcnpm.dto.AddressResponseDTO;
import com.project.nmcnpm.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        try {
            AddressResponseDTO createdAddress = addressService.createAddress(addressDTO);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            System.err.println("Error creating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Integer id) {
        try {
            AddressResponseDTO addressDTO = addressService.getAddressById(id);
            return new ResponseEntity<>(addressDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error getting address by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Integer id, @RequestBody AddressDTO addressDTO) {
        try {
            AddressResponseDTO updatedAddress = addressService.updateAddress(id, addressDTO);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error updating address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{addressId}/user/{userId}") 
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId, @PathVariable Integer userId) {
        try {
            addressService.deleteAddress(addressId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
        } catch (Exception e) {
            System.err.println("Error deleting address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAllAddresses(Pageable pageable) {
        try {
            Page<AddressResponseDTO> addressesPage = addressService.getAllAddresses(pageable);
            return new ResponseEntity<>(addressesPage, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error getting all addresses: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUserId(@PathVariable Integer userId) {
        try {
            List<AddressResponseDTO> addresses = addressService.getAddressesByUserId(userId);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            System.err.println("Error getting addresses by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/{userId}/default")
    public ResponseEntity<AddressResponseDTO> getDefaultAddressByUserId(@PathVariable Integer userId) {
        try {
            AddressResponseDTO defaultAddress = addressService.getDefaultAddressByUserId(userId);
            return new ResponseEntity<>(defaultAddress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            System.err.println("Error getting default address by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/user/{userId}/default/{addressId}")
    public ResponseEntity<AddressResponseDTO> setDefaultAddress(@PathVariable Integer userId, @PathVariable Integer addressId) {
        try {
            AddressResponseDTO updatedAddress = addressService.setDefaultAddress(userId, addressId);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (Exception e) {
            System.err.println("Error setting default address: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}