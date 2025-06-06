package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ShopDTO;
import com.project.nmcnpm.dto.ShopResponseDTO; 
import com.project.nmcnpm.service.ShopService;
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
@RequestMapping("/api/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<ShopResponseDTO> createShop(@Valid @RequestBody ShopDTO shopDTO) {
        try {
            ShopResponseDTO createdShop = shopService.createShop(shopDTO);
            return new ResponseEntity<>(createdShop, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ví dụ: User không tồn tại
        } catch (IllegalArgumentException e) { 
            System.err.println("Validation error creating shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Ví dụ: User đã có shop
        } catch (Exception e) {
            System.err.println("Internal server error creating shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponseDTO> getShopById(@PathVariable Integer id) {
        try {
            ShopResponseDTO shop = shopService.getShopById(id);
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shop not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shop by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopResponseDTO> updateShop(@PathVariable Integer id, @Valid @RequestBody ShopDTO shopDTO) {
        try {
            ShopResponseDTO updatedShop = shopService.updateShop(id, shopDTO);
            return new ResponseEntity<>(updatedShop, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shop or user not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error updating shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Ví dụ: User mới đã có shop khác
        } catch (Exception e) {
            System.err.println("Internal server error updating shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id) {
        try {
            shopService.deleteShop(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Shop not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ShopResponseDTO>> getAllShops(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShopResponseDTO> shops = shopService.getAllShops(pageable);
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ShopResponseDTO> getShopByUserId(@PathVariable Integer userId) {
        try {
            ShopResponseDTO shop = shopService.getShopByUserId(userId);
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Shop not found for user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shop by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShopResponseDTO>> searchShopsByName(
            @RequestParam String name) {
        try {
            List<ShopResponseDTO> shops = shopService.searchShopsByName(name);
            return new ResponseEntity<>(shops, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Internal server error searching shops by name: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}