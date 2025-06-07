package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.PaymentRepository;
import com.project.nmcnpm.dao.OrderRepository; 
import com.project.nmcnpm.dto.PaymentDTO;
import com.project.nmcnpm.dto.PaymentResponseDTO;
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.Payment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImplementation implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    public PaymentServiceImplementation(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    @Transactional
    public Payment createPayment(PaymentDTO paymentDTO) {
        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + paymentDTO.getOrderId()));
        if (paymentRepository.findByOrderOrderId(paymentDTO.getOrderId()) != null) {
            throw new IllegalArgumentException("Payment already exists for order with ID: " + paymentDTO.getOrderId());
        }
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus() != null ? paymentDTO.getPaymentStatus() : Payment.PaymentStatus.Pending);
        payment.setAmount(paymentDTO.getAmount());
        order.setPayment(payment);
        orderRepository.save(order); 
        return paymentRepository.save(payment);
    }
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
        return new PaymentResponseDTO(payment);
    }
    @Override
    @Transactional
    public Payment updatePaymentStatus(Integer paymentId, Payment.PaymentStatus newStatus) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
        existingPayment.setPaymentStatus(newStatus);
        return paymentRepository.save(existingPayment);
    }
    @Override
    @Transactional
    public Payment updatePayment(Integer paymentId, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
        if (paymentDTO.getPaymentMethod() != null) {
            existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        }
        if (paymentDTO.getPaymentStatus() != null) {
            existingPayment.setPaymentStatus(paymentDTO.getPaymentStatus());
        }
        if (paymentDTO.getAmount() != null) {
            existingPayment.setAmount(paymentDTO.getAmount());
        }
        return paymentRepository.save(existingPayment);
    }
    @Override
    @Transactional
    public void deletePayment(Integer paymentId) {
        Payment paymentToDelete = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
        if (paymentToDelete.getOrder() != null) {
            paymentToDelete.getOrder().setPayment(null);
            orderRepository.save(paymentToDelete.getOrder());
        }
        paymentRepository.delete(paymentToDelete);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getAllPayments(Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findAll(pageable);
        return paymentsPage.map(PaymentResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentByOrderId(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }
        Payment payment = paymentRepository.findByOrderOrderId(orderId);
        if (payment == null) {
            throw new EntityNotFoundException("Payment not found for order with id: " + orderId);
        }
        return new PaymentResponseDTO(payment);
    }
}
