package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT DISTINCT o FROM Order o " +
           "JOIN FETCH o.user u " +
           "JOIN FETCH o.address a " +
           "LEFT JOIN FETCH o.productsInOrder pio " + 
           "LEFT JOIN FETCH pio.shop s " +
           "WHERE o.orderId = :orderId")
    Optional<Order> findByIdWithDetails(@Param("orderId") Integer orderId);
    @Query(value = "SELECT DISTINCT o FROM Order o " +
                   "JOIN FETCH o.user u " +
                   "JOIN FETCH o.address a " +
                   "LEFT JOIN FETCH o.productsInOrder pio " +
                   "LEFT JOIN FETCH pio.shop s",
           countQuery = "SELECT count(o) FROM Order o")
    Page<Order> findAllWithDetails(Pageable pageable);
    @Query(value = "SELECT DISTINCT o FROM Order o " +
                   "JOIN FETCH o.user u " +
                   "JOIN FETCH o.address a " +
                   "LEFT JOIN FETCH o.productsInOrder pio " +
                   "LEFT JOIN FETCH pio.shop s " +
                   "WHERE o.user = :user",
           countQuery = "SELECT count(o) FROM Order o WHERE o.user = :user")
    Page<Order> findByUserWithDetails(@Param("user") User user, Pageable pageable);
}