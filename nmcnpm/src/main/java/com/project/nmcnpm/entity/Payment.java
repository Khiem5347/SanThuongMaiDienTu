package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.Pending;
    @Column(name = "payment_time")
    @CreationTimestamp
    private Date paymentTime;
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    public enum PaymentMethod {
        COD, CreditCard, BankTransfer
    }
    public enum PaymentStatus {
        Pending, Completed, Failed
    }
    public Payment() {
    }
    public Payment(Integer paymentId, Order order, PaymentMethod paymentMethod, PaymentStatus paymentStatus, Date paymentTime, BigDecimal amount) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.amount = amount;
    }
    public Integer getPaymentId() {
        return paymentId;
    }
    public Order getOrder() {
        return order;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public Date getPaymentTime() {
        return paymentTime;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}