package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.User; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByUser(User user, Pageable pageable);
}