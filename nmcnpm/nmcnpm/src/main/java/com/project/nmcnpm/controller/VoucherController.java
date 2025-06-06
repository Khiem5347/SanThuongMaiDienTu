package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.VoucherDTO;
import com.project.nmcnpm.dto.VoucherResponseDTO;
import com.project.nmcnpm.entity.Voucher;
import com.project.nmcnpm.service.VoucherService;
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
@RequestMapping("/api/vouchers")
public class VoucherController {
    private final VoucherService voucherService;
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@Valid @RequestBody VoucherDTO voucherDTO) {
        try {
            Voucher createdVoucher = voucherService.createVoucher(voucherDTO);
            return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        } catch (Exception e) {
            System.err.println("Internal server error creating voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDTO> getVoucherById(@PathVariable Integer id) {
        try {
            VoucherResponseDTO voucher = voucherService.getVoucherById(id);
            return new ResponseEntity<>(voucher, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Voucher not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting voucher by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/code/{code}")
    public ResponseEntity<VoucherResponseDTO> getVoucherByCode(@PathVariable String code) {
        try {
            VoucherResponseDTO voucher = voucherService.getVoucherByCode(code);
            return new ResponseEntity<>(voucher, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Voucher not found by code: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting voucher by code: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable Integer id, @Valid @RequestBody VoucherDTO voucherDTO) {
        try {
            Voucher updatedVoucher = voucherService.updateVoucher(id, voucherDTO);
            return new ResponseEntity<>(updatedVoucher, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Voucher not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error updating voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        } catch (Exception e) {
            System.err.println("Internal server error updating voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Integer id) {
        try {
            voucherService.deleteVoucher(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Voucher not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting voucher: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<VoucherResponseDTO>> getAllVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoucherResponseDTO> vouchers = voucherService.getAllVouchers(pageable);
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }
    @GetMapping("/active")
    public ResponseEntity<List<VoucherResponseDTO>> getActiveVouchers() {
        try {
            List<VoucherResponseDTO> activeVouchers = voucherService.getActiveVouchers();
            return new ResponseEntity<>(activeVouchers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Internal server error getting active vouchers: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/type/{discountType}")
    public ResponseEntity<List<VoucherResponseDTO>> getVouchersByDiscountType(@PathVariable Voucher.DiscountType discountType) {
        try {
            List<VoucherResponseDTO> vouchers = voucherService.getVouchersByDiscountType(discountType);
            return new ResponseEntity<>(vouchers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Internal server error getting vouchers by discount type: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
