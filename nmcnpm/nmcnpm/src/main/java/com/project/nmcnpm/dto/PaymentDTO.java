package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Payment.PaymentMethod;
import com.project.nmcnpm.entity.Payment.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentDTO {
    @NotNull(message = "Order ID cannot be null")
    private Integer orderId;
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;
    public PaymentDTO() {
    }
    public PaymentDTO(Integer orderId, PaymentMethod paymentMethod, PaymentStatus paymentStatus, BigDecimal amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
