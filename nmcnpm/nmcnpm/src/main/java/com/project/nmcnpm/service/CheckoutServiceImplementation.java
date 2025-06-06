package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.Purchase;
import com.project.nmcnpm.dto.PurchaseResponse; 
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.ProductsInOrder;
import com.project.nmcnpm.dao.OrderRepository; 
import com.project.nmcnpm.dao.ProductsInOrderRepository;
import jakarta.persistence.EntityNotFoundException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashSet; 
import java.util.List;
import java.util.Set; 
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImplementation implements CheckoutService {
    private OrderRepository orderRepository;
    private ProductsInOrderRepository productsInOrderRepository;
    @Autowired
    public CheckoutServiceImplementation(OrderRepository orderRepository, ProductsInOrderRepository productsInOrderRepository) {
        this.orderRepository = orderRepository;
        this.productsInOrderRepository = productsInOrderRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) { 
        Order order = purchase.getOrder();
        order.setUser(purchase.getCustomer());
        List<ProductsInOrder> productsInOrderList = purchase.getOrderItems();
        for (ProductsInOrder item : productsInOrderList) {
            order.addProductInOrder(item); 
        }
        BigDecimal totalAmount = productsInOrderList.stream()
                .map(item -> item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add); 
        order.setTotalAmount(totalAmount); 
        orderRepository.save(order);
        for (ProductsInOrder item : productsInOrderList) {
            item.setOrder(order); 
            productsInOrderRepository.save(item);
        }
        return new PurchaseResponse("Order placed successfully for user: " + order.getUser().getUsername() + " with total amount: " + order.getTotalAmount());
    }
}
