package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.OrderDTO;
import com.project.nmcnpm.dto.OrderResponseDTO;
import com.project.nmcnpm.entity.Order; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderDTO orderDTO);
    OrderResponseDTO getOrderById(Integer orderId);
    OrderResponseDTO updateOrderStatus(Integer orderId, String newStatus); 
    OrderResponseDTO updateOrder(Integer orderId, OrderDTO orderDTO); 
    void deleteOrder(Integer orderId);
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    Page<OrderResponseDTO> getOrdersByUserId(Integer userId, Pageable pageable);
    OrderResponseDTO getOrderByTrackingNumber(String orderTrackingNumber);
}
