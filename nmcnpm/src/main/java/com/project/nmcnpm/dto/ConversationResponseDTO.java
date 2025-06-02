package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Conversation;
import com.project.nmcnpm.entity.User; 
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationResponseDTO {
    private Integer conversationId;
    private Integer buyerId;
    private String buyerUsername; 
    private Integer sellerId;
    private String sellerUsername;
    private Date startedAt;
    private List<MessageResponseDTO> messages; 
    public ConversationResponseDTO() {
    }
    public ConversationResponseDTO(Conversation conversation) {
        this.conversationId = conversation.getConversationId();
        this.startedAt = conversation.getStartedAt();
        if (conversation.getBuyer() != null) {
            this.buyerId = conversation.getBuyer().getUserId();
            this.buyerUsername = conversation.getBuyer().getUsername();
        }
        if (conversation.getSeller() != null) {
            this.sellerId = conversation.getSeller().getUserId();
            this.sellerUsername = conversation.getSeller().getUsername();
        }
        if (conversation.getMessages() != null && !conversation.getMessages().isEmpty()) {
            // Sort messages by time for consistent display
            this.messages = conversation.getMessages().stream()
                    .map(MessageResponseDTO::new)
                    .sorted((m1, m2) -> m1.getMessageTime().compareTo(m2.getMessageTime()))
                    .collect(Collectors.toList());
        } else {
            this.messages = List.of(); 
        }
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public Integer getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }
    public String getBuyerUsername() {
        return buyerUsername;
    }
    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }
    public Integer getSellerId() {
        return sellerId;
    }
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
    public String getSellerUsername() {
        return sellerUsername;
    }
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
    public Date getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
    public List<MessageResponseDTO> getMessages() {
        return messages;
    }
    public void setMessages(List<MessageResponseDTO> messages) {
        this.messages = messages;
    }
}
