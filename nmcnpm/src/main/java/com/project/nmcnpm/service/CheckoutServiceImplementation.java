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

        // Attach order to each ProductsInOrder item and calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO; // Initialize totalAmount to BigDecimal.ZERO
        for (ProductsInOrder item : productsInOrderList) {
            order.addProductInOrder(item); // Add item to order's collection

            // Convert Integer productPrice to BigDecimal before multiplication
            BigDecimal itemPrice = BigDecimal.valueOf(item.getProductPrice());
            BigDecimal itemQuantity = BigDecimal.valueOf(item.getProductQuantity());

            totalAmount = totalAmount.add(itemPrice.multiply(itemQuantity));
        }

        // Set the calculated total amount to the order
        order.setTotalAmount(totalAmount); // Set BigDecimal to BigDecimal

        // Save the order first to get its ID
        orderRepository.save(order);

        // Ensure productsInOrder items are associated with the saved order and then save them
        for (ProductsInOrder item : productsInOrderList) {
            item.setOrder(order); // Associate the item with the saved order
            productsInOrderRepository.save(item); // Save individual ProductsInOrder items
        }

        return new PurchaseResponse("Order placed successfully for user: " + order.getUser().getUsername());
    }
}