package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByOrderOrderId(Integer orderId);
}