package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.OrderRepository;
import com.project.nmcnpm.dao.ProductsInOrderRepository;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.dao.AddressRepository;
import com.project.nmcnpm.dao.ProductRepository;
import com.project.nmcnpm.dao.ShopRepository;
import com.project.nmcnpm.dto.OrderDTO;
import com.project.nmcnpm.dto.OrderItemDTO;
import com.project.nmcnpm.dto.OrderResponseDTO;
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.ProductsInOrder;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.math.BigDecimal;
import java.util.UUID; 
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductsInOrderRepository productsInOrderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    public OrderServiceImplementation(OrderRepository orderRepository,
                                      ProductsInOrderRepository productsInOrderRepository,
                                      UserRepository userRepository,
                                      AddressRepository addressRepository,
                                      ProductRepository productRepository,
                                      ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.productsInOrderRepository = productsInOrderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }
    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + orderDTO.getUserId()));
        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + orderDTO.getAddressId()));
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : "PENDING"); // Default status
        order.setOrderTrackingNumber(generateOrderTrackingNumber());
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + itemDTO.getProductId()));
            Shop shop = shopRepository.findById(itemDTO.getShopId())
                    .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + itemDTO.getShopId()));
            ProductsInOrder productsInOrder = new ProductsInOrder();
            productsInOrder.setProduct(product);
            productsInOrder.setShop(shop);
            productsInOrder.setProductName(product.getProductName()); 
            productsInOrder.setColor(itemDTO.getColor());
            productsInOrder.setSize(itemDTO.getSize());
            productsInOrder.setProductImageUrl(itemDTO.getProductImageUrl() != null ? itemDTO.getProductImageUrl() : product.getProductMainImageUrl()); // Use DTO's image or product's main image
            productsInOrder.setProductPrice(itemDTO.getProductPrice());
            productsInOrder.setProductQuantity(itemDTO.getProductQuantity());
            order.addProductInOrder(productsInOrder); 
            totalAmount = totalAmount.add(itemDTO.getProductPrice().multiply(BigDecimal.valueOf(itemDTO.getProductQuantity())));
        }
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDTO(savedOrder);
    }
    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        return new OrderResponseDTO(order);
    }
    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Integer orderId, String newStatus) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        existingOrder.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(existingOrder);
        return new OrderResponseDTO(updatedOrder);
    }
    @Override
    @Transactional
    public OrderResponseDTO updateOrder(Integer orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        if (orderDTO.getUserId() != null &&
            (existingOrder.getUser() == null || !existingOrder.getUser().getUserId().equals(orderDTO.getUserId()))) {
            User newUser = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + orderDTO.getUserId()));
            existingOrder.setUser(newUser);
        }
        if (orderDTO.getAddressId() != null &&
            (existingOrder.getAddress() == null || !existingOrder.getAddress().getAddressId().equals(orderDTO.getAddressId()))) {
            Address newAddress = addressRepository.findById(orderDTO.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + orderDTO.getAddressId()));
            existingOrder.setAddress(newAddress);
        }
        if (orderDTO.getStatus() != null && !orderDTO.getStatus().isEmpty()) {
            existingOrder.setStatus(orderDTO.getStatus());
        }
        Order updatedOrder = orderRepository.save(existingOrder);
        return new OrderResponseDTO(updatedOrder);
    }
    @Override
    @Transactional
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findAll(pageable);
        return ordersPage.map(OrderResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUserId(Integer userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        Page<Order> ordersPage = orderRepository.findByUserUserId(userId, pageable);
        return ordersPage.map(OrderResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderByTrackingNumber(String orderTrackingNumber) {
        Order order = orderRepository.findByOrderTrackingNumber(orderTrackingNumber);
        if (order == null) {
            throw new EntityNotFoundException("Order not found with tracking number: " + orderTrackingNumber);
        }
        return new OrderResponseDTO(order);
    }
    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase(); // Example: 16-char alphanumeric
    }
}
