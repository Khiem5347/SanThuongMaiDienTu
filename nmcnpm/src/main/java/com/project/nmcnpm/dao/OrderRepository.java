package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserUserId(Integer userId);
    Page<Order> findByUserUserId(Integer userId, Pageable pageable);
    Order findByOrderTrackingNumber(String orderTrackingNumber);
}