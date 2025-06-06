package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MessageDTO {
    @NotNull(message = "Conversation ID cannot be null")
    private Integer conversationId;
    @NotNull(message = "Sender ID cannot be null")
    private Integer senderId;
    @NotBlank(message = "Message text cannot be blank")
    @Size(max = 1000, message = "Message text cannot exceed 1000 characters")
    private String messText;
    public MessageDTO() {
    }
    public MessageDTO(Integer conversationId, Integer senderId, String messText) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.messText = messText;
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public String getMessText() {
        return messText;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public void setMessText(String messText) {
        this.messText = messText;
    }
}
