package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    @Column(name = "mess_text", columnDefinition = "TEXT")
    private String messText;
    @Column(name = "message_time")
    @CreationTimestamp
    private Date messageTime;
    @Column(name = "is_read")
    private Boolean isRead = false;
    public Message() {
    }
    public Message(Integer messageId, Conversation conversation, User sender, String messText, Date messageTime, Boolean isRead) {
        this.messageId = messageId;
        this.conversation = conversation;
        this.sender = sender;
        this.messText = messText;
        this.messageTime = messageTime;
        this.isRead = isRead;
    }
    public Integer getMessageId() {
        return messageId;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public User getSender() {
        return sender;
    }
    public String getMessText() {
        return messText;
    }
    public Date getMessageTime() {
        return messageTime;
    }
    public Boolean getIsRead() {
        return isRead;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setMessText(String messText) {
        this.messText = messText;
    }
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
    public void setIsRead(Boolean read) {
        isRead = read;
    }
}