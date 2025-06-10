package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Historys")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    public History() {
    }
    public History(Integer historyId, User user, Order order) {
        this.historyId = historyId;
        this.user = user;
        this.order = order;
    }
    public Integer getHistoryId() {
        return historyId;
    }
    public User getUser() {
        return user;
    }
    public Order getOrder() {
        return order;
    }
    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}