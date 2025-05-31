package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.HashSet; 
import java.util.Set;
@Entity
@Table(name = "Conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Integer conversationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    @Column(name = "started_at")
    @CreationTimestamp
    private Date startedAt;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages = new HashSet<>(); 
    public Conversation() {
    }
    public Conversation(Integer conversationId, User buyer, User seller, Date startedAt, Set<Message> messages) {
        this.conversationId = conversationId;
        this.buyer = buyer;
        this.seller = seller;
        this.startedAt = startedAt;
        this.messages = messages;
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public User getBuyer() {
        return buyer;
    }
    public User getSeller() {
        return seller;
    }
    public Date getStartedAt() {
        return startedAt;
    }
    public Set<Message> getMessages() {
        return messages;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
    public void setSeller(User seller) {
        this.seller = seller;
    }
    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    public void addMessage(Message message) {
        if (message != null) {
            if (this.messages == null) {
                this.messages = new HashSet<>();
            }
            this.messages.add(message);
            message.setConversation(this); 
        }
    }
    public void removeMessage(Message message) {
        if (message != null && this.messages != null) {
            this.messages.remove(message);
            message.setConversation(null);
        }
    }
}