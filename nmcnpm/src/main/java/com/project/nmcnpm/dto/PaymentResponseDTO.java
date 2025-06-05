package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Payment;
import com.project.nmcnpm.entity.Order; 
import java.math.BigDecimal;
import java.util.Date;

public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer orderId;
    private Payment.PaymentMethod paymentMethod;
    private Payment.PaymentStatus paymentStatus;
    private Date paymentTime;
    private BigDecimal amount;
    public PaymentResponseDTO() {
    }
    public PaymentResponseDTO(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.paymentMethod = payment.getPaymentMethod();
        this.paymentStatus = payment.getPaymentStatus();
        this.paymentTime = payment.getPaymentTime();
        this.amount = payment.getAmount();
        if (payment.getOrder() != null) {
            this.orderId = payment.getOrder().getOrderId();
        }
    }
    public Integer getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public Payment.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(Payment.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public Date getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
