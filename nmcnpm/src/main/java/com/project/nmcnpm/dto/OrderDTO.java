package com.project.nmcnpm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class OrderDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotNull(message = "Address ID cannot be null")
    private Integer addressId;
    @Size(min = 1, message = "Order must contain at least one item")
    @Valid 
    private List<OrderItemDTO> items;
    private String status;
    public OrderDTO() {
    }
    public OrderDTO(Integer userId, Integer addressId, List<OrderItemDTO> items, String status) {
        this.userId = userId;
        this.addressId = addressId;
        this.items = items;
        this.status = status;
    }
    public Integer getUserId() {
        return userId;
    }
    public Integer getAddressId() {
        return addressId;
    }
    public List<OrderItemDTO> getItems() {
        return items;
    }
    public String getStatus() {
        return status;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
