package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.AppliedVoucherDTO;
import com.project.nmcnpm.dto.AppliedVoucherResponseDTO; 
import com.project.nmcnpm.service.AppliedVoucherService;
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
@RequestMapping("/api/applied-vouchers")
public class AppliedVoucherController {
    private final AppliedVoucherService appliedVoucherService;

    public AppliedVoucherController(AppliedVoucherService appliedVoucherService) {
        this.appliedVoucherService = appliedVoucherService;
    }

    @PostMapping
    public ResponseEntity<AppliedVoucherResponseDTO> createAppliedVoucher(@Valid @RequestBody AppliedVoucherDTO appliedVoucherDTO) {
        try {
            AppliedVoucherResponseDTO createdAppliedVoucher = appliedVoucherService.createAppliedVoucher(appliedVoucherDTO);
            return new ResponseEntity<>(createdAppliedVoucher, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating applied voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating applied voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        } catch (Exception e) {
            System.err.println("Internal server error creating applied voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppliedVoucherResponseDTO> getAppliedVoucherById(@PathVariable Integer id) {
        try {
            AppliedVoucherResponseDTO appliedVoucher = appliedVoucherService.getAppliedVoucherById(id);
            return new ResponseEntity<>(appliedVoucher, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Applied Voucher not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting applied voucher by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppliedVoucher(@PathVariable Integer id) {
        try {
            appliedVoucherService.deleteAppliedVoucher(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Applied Voucher not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting applied voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<AppliedVoucherResponseDTO>> getAllAppliedVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppliedVoucherResponseDTO> appliedVouchers = appliedVoucherService.getAllAppliedVouchers(pageable);
        return new ResponseEntity<>(appliedVouchers, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<AppliedVoucherResponseDTO>> getAppliedVouchersByCategoryId(@PathVariable Integer categoryId) {
        try {
            List<AppliedVoucherResponseDTO> appliedVouchers = appliedVoucherService.getAppliedVouchersByCategoryId(categoryId);
            return new ResponseEntity<>(appliedVouchers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Category not found when fetching applied vouchers: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting applied vouchers by category ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<AppliedVoucherResponseDTO>> getAppliedVouchersByVoucherId(@PathVariable Integer voucherId) {
        try {
            List<AppliedVoucherResponseDTO> appliedVouchers = appliedVoucherService.getAppliedVouchersByVoucherId(voucherId);
            return new ResponseEntity<>(appliedVouchers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Voucher not found when fetching applied vouchers: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting applied vouchers by voucher ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}