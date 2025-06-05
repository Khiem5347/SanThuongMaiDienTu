package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.OrderDTO;
import com.project.nmcnpm.dto.OrderResponseDTO;
import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.ProductsInOrder;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.Shop;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.dao.AddressRepository; 
import com.project.nmcnpm.dao.OrderRepository; 
import com.project.nmcnpm.dao.ProductRepository; 
import com.project.nmcnpm.dao.ShopRepository; 
import com.project.nmcnpm.dao.UserRepository; 
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    public OrderServiceImplementation(OrderRepository orderRepository, UserRepository userRepository,
                                      AddressRepository addressRepository, ProductRepository productRepository,
                                      ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }
    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + orderDTO.getUserId()));
        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + orderDTO.getAddressId()));
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getItems()) { 
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + itemDTO.getProductId()));
                Shop shop = shopRepository.findById(itemDTO.getShopId())
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + itemDTO.getShopId()));

                ProductsInOrder productsInOrder = new ProductsInOrder();
                productsInOrder.setProduct(product);
                productsInOrder.setShop(shop);
                productsInOrder.setProductName(itemDTO.getProductName());
                productsInOrder.setColor(itemDTO.getColor());
                productsInOrder.setSize(itemDTO.getSize());
                productsInOrder.setProductImageUrl(itemDTO.getProductImageUrl());
                productsInOrder.setProductPrice(itemDTO.getProductPrice());
                productsInOrder.setProductQuantity(itemDTO.getProductQuantity());
                order.addProductInOrder(productsInOrder);
            }
        }
        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDTO(savedOrder);
    }
    @Override
    public OrderResponseDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        return new OrderResponseDTO(order);
    }
    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Integer id, String newStatus) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        return new OrderResponseDTO(existingOrder);
    }
    @Override
    @Transactional
    public OrderResponseDTO updateOrder(Integer id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + orderDTO.getUserId()));
        existingOrder.setUser(user);
        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + orderDTO.getAddressId()));
        existingOrder.setAddress(address);
        if (orderDTO.getItems() != null) {
            existingOrder.getProductsInOrder().clear();
            for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getItems()) { 
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + itemDTO.getProductId()));
                Shop shop = shopRepository.findById(itemDTO.getShopId())
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + itemDTO.getShopId()));
                ProductsInOrder productsInOrder = new ProductsInOrder();
                productsInOrder.setProduct(product);
                productsInOrder.setShop(shop);
                productsInOrder.setProductName(itemDTO.getProductName());
                productsInOrder.setColor(itemDTO.getColor());
                productsInOrder.setSize(itemDTO.getSize());
                productsInOrder.setProductImageUrl(itemDTO.getProductImageUrl());
                productsInOrder.setProductPrice(itemDTO.getProductPrice());
                productsInOrder.setProductQuantity(itemDTO.getProductQuantity());
                existingOrder.addProductInOrder(productsInOrder); 
            }
        }
        Order savedOrder = orderRepository.save(existingOrder);
        return new OrderResponseDTO(savedOrder);
    }
    @Override
    @Transactional
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findAll(pageable);
        List<OrderResponseDTO> dtoList = ordersPage.getContent().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, ordersPage.getTotalElements());
    }
    @Override
    public Page<OrderResponseDTO> getOrdersByUserId(Integer userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Page<Order> ordersPage = orderRepository.findByUser(user, pageable);
        List<OrderResponseDTO> dtoList = ordersPage.getContent().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, ordersPage.getTotalElements());
    }
    @Override
    public OrderResponseDTO getOrderByTrackingNumber(String trackingNumber) {
        throw new UnsupportedOperationException("getOrderByTrackingNumber is not supported as 'orderTrackingNumber' field is removed from Order entity.");
    }
}
