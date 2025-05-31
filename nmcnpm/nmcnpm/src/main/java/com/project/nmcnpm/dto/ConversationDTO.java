package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotNull;

public class ConversationDTO {
    @NotNull(message = "Buyer ID cannot be null")
    private Integer buyerId;
    @NotNull(message = "Seller ID cannot be null")
    private Integer sellerId;
    public ConversationDTO() {
    }
    public ConversationDTO(Integer buyerId, Integer sellerId) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }
    public Integer getBuyerId() {
        return buyerId;
    }
    public Integer getSellerId() {
        return sellerId;
    }
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
}
