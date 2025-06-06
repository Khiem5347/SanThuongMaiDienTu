package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.PaymentDTO;
import com.project.nmcnpm.dto.PaymentResponseDTO;
import com.project.nmcnpm.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface PaymentService {
    Payment createPayment(PaymentDTO paymentDTO);
    PaymentResponseDTO getPaymentById(Integer paymentId);
    Payment updatePaymentStatus(Integer paymentId, Payment.PaymentStatus newStatus);
    Payment updatePayment(Integer paymentId, PaymentDTO paymentDTO);
    void deletePayment(Integer paymentId);
    Page<PaymentResponseDTO> getAllPayments(Pageable pageable);
    PaymentResponseDTO getPaymentByOrderId(Integer orderId);
}
