package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.History;
import com.project.nmcnpm.entity.User;  
import com.project.nmcnpm.entity.Order; 

public class HistoryResponseDTO {
    private Integer historyId;
    private Integer userId;
    private String userName; 
    private Integer orderId;
    private String orderTrackingNumber; 
    private String orderStatus;
    public HistoryResponseDTO() {
    }
    public HistoryResponseDTO(History history) {
        this.historyId = history.getHistoryId();
        if (history.getUser() != null) {
            this.userId = history.getUser().getUserId();
            this.userName = history.getUser().getUsername();
        }
        if (history.getOrder() != null) {
            this.orderId = history.getOrder().getOrderId();
            this.orderTrackingNumber = history.getOrder().getOrderTrackingNumber();
            this.orderStatus = history.getOrder().getStatus();
        }
    }
    public Integer getHistoryId() {
        return historyId;
    }
    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }
    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
