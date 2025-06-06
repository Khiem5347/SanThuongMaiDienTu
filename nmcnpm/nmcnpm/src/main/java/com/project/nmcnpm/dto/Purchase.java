package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Address; 
import com.project.nmcnpm.entity.Order;   
import com.project.nmcnpm.entity.ProductsInOrder; 
import com.project.nmcnpm.entity.User;    
import java.util.List;

public class Purchase {
    private Order order; 
    private List<ProductsInOrder> orderItems; 
    private User customer; 
    private Address shippingAddress; 
    public Purchase() {
    }
    public Purchase(Order order, List<ProductsInOrder> orderItems, User customer, Address shippingAddress) {
        this.order = order;
        this.orderItems = orderItems;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
    }
    public Order getOrder() {
        return order;
    }
    public List<ProductsInOrder> getOrderItems() {
        return orderItems;
    }
    public User getCustomer() {
        return customer;
    }
    public Address getShippingAddress() {
        return shippingAddress;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setOrderItems(List<ProductsInOrder> orderItems) {
        this.orderItems = orderItems;
    }
    public void setCustomer(User customer) {
        this.customer = customer;
    }
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}