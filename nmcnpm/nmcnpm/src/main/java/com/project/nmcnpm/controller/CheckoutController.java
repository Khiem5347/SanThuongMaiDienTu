package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.Purchase;
import com.project.nmcnpm.dto.PurchaseResponse;
import com.project.nmcnpm.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private CheckoutService checkoutService;
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody Purchase purchase) {
        try {
            PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
            return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return new ResponseEntity<>(new PurchaseResponse("Error processing purchase: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new PurchaseResponse("An unexpected error occurred during purchase."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}