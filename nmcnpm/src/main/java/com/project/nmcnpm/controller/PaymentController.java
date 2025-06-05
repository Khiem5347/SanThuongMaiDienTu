package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.PaymentDTO;
import com.project.nmcnpm.dto.PaymentResponseDTO; 
import com.project.nmcnpm.entity.Payment;
import com.project.nmcnpm.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) { // Changed return type
        try {
            Payment createdPayment = paymentService.createPayment(paymentDTO);
            PaymentResponseDTO responseDTO = paymentService.getPaymentById(createdPayment.getPaymentId());
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.err.println("Internal server error creating payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Integer id) {
        try {
            PaymentResponseDTO payment = paymentService.getPaymentById(id);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Payment not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting payment by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus( 
            @PathVariable Integer id,
            @RequestParam Payment.PaymentStatus newStatus) {
        try {
            Payment updatedPayment = paymentService.updatePaymentStatus(id, newStatus);
            PaymentResponseDTO responseDTO = paymentService.getPaymentById(updatedPayment.getPaymentId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Payment not found for status update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating payment status: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Integer id, @Valid @RequestBody PaymentDTO paymentDTO) { // Changed return type
        try {
            Payment updatedPayment = paymentService.updatePayment(id, paymentDTO);
            PaymentResponseDTO responseDTO = paymentService.getPaymentById(updatedPayment.getPaymentId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Payment not found during update: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error updating payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Payment not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentResponseDTO> payments = paymentService.getAllPayments(pageable);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable Integer orderId) {
        try {
            PaymentResponseDTO payment = paymentService.getPaymentByOrderId(orderId);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Payment not found for order ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting payment by order ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
