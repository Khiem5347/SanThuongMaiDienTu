package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p JOIN FETCH p.order o WHERE p.paymentId = :paymentId")
    Optional<Payment> findByIdWithOrder(@Param("paymentId") Integer paymentId);
    @Query("SELECT p FROM Payment p JOIN FETCH p.order o WHERE p.order.orderId = :orderId")
    Payment findByOrderOrderIdWithOrder(@Param("orderId") Integer orderId);
    @Query(value = "SELECT p FROM Payment p JOIN FETCH p.order o",
           countQuery = "SELECT count(p) FROM Payment p")
    Page<Payment> findAllWithOrder(Pageable pageable);
    Payment findByOrderOrderId(Integer orderId);
}
