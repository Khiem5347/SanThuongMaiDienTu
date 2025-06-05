package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Message;
import com.project.nmcnpm.entity.User;
import java.util.Date;

public class MessageResponseDTO {
    private Integer messageId;
    private Integer conversationId;
    private Integer senderId;
    private String senderUsername; 
    private String messText;
    private Date messageTime;
    private Boolean isRead;
    public MessageResponseDTO() {
    }
    public MessageResponseDTO(Message message) {
        this.messageId = message.getMessageId();
        this.messText = message.getMessText();
        this.messageTime = message.getMessageTime();
        this.isRead = message.getIsRead();
        if (message.getConversation() != null) {
            this.conversationId = message.getConversation().getConversationId();
        }
        if (message.getSender() != null) {
            this.senderId = message.getSender().getUserId();
            this.senderUsername = message.getSender().getUsername(); 
        }
    }
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getSenderUsername() {
        return senderUsername;
    }
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
    public String getMessText() {
        return messText;
    }
    public void setMessText(String messText) {
        this.messText = messText;
    }
    public Date getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
