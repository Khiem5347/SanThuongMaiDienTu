package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.User;    
import com.project.nmcnpm.entity.Address; 
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDTO {
    private Integer orderId;
    private Integer userId;
    private String userName; 
    private Integer addressId;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;
    private Integer totalItemsQuantity; 
    public OrderResponseDTO() {
    }
    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        User user = order.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername(); 
        }
        Address address = order.getAddress();
        if (address != null) {
            this.addressId = address.getAddressId();
        }
        this.totalAmount = order.getTotalAmount();
        if (order.getProductsInOrder() != null && !order.getProductsInOrder().isEmpty()) {
            this.items = order.getProductsInOrder().stream()
                    .map(OrderItemResponseDTO::new)
                    .collect(Collectors.toList());
            this.totalItemsQuantity = this.items.stream()
                    .mapToInt(OrderItemResponseDTO::getProductQuantity)
                    .sum();
        } else {
            this.items = List.of(); 
            this.totalItemsQuantity = 0;
        }
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<OrderItemResponseDTO> getItems() {
        return items;
    }
    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }
    public Integer getTotalItemsQuantity() {
        return totalItemsQuantity;
    }
    public void setTotalItemsQuantity(Integer totalItemsQuantity) {
        this.totalItemsQuantity = totalItemsQuantity;
    }
}
