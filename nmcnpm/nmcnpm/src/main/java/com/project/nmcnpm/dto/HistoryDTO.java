package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotNull;

public class HistoryDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotNull(message = "Order ID cannot be null")
    private Integer orderId;
    public HistoryDTO() {
    }
    public HistoryDTO(Integer userId, Integer orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }
    public Integer getUserId() {
        return userId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
