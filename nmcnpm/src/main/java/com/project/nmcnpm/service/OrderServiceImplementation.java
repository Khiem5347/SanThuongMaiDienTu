package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.AddressRepository;
import com.project.nmcnpm.dao.OrderRepository;
import com.project.nmcnpm.dao.ProductRepository;
import com.project.nmcnpm.dao.ShopRepository;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.dao.VoucherRepository;
import com.project.nmcnpm.dto.OrderDTO;
import com.project.nmcnpm.dto.OrderResponseDTO;
import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.ProductsInOrder;
import com.project.nmcnpm.entity.Shop;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.entity.Voucher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
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
    private final VoucherRepository voucherRepository;

    public OrderServiceImplementation(OrderRepository orderRepository, UserRepository userRepository,
                                      AddressRepository addressRepository, ProductRepository productRepository,
                                      ShopRepository shopRepository, VoucherRepository voucherRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + orderDTO.getUserId()));
        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + orderDTO.getAddressId()));

        Order order = new Order(user, address, BigDecimal.ZERO);

        BigDecimal totalAmount = BigDecimal.ZERO;
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getItems()) {
                com.project.nmcnpm.entity.Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + itemDTO.getProductId()));
                Shop shop = shopRepository.findById(itemDTO.getShopId())
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + itemDTO.getShopId()));

                ProductsInOrder productsInOrder = new ProductsInOrder();
                productsInOrder.setOrder(order);
                productsInOrder.setShop(shop);
                productsInOrder.setProductName(itemDTO.getProductName());
                productsInOrder.setColor(itemDTO.getColor());
                productsInOrder.setSize(itemDTO.getSize());
                productsInOrder.setProductImageUrl(itemDTO.getProductImageUrl());
                productsInOrder.setProductPrice(itemDTO.getProductPrice());

                if (itemDTO.getProductQuantity() == null || itemDTO.getProductQuantity() <= 0) {
                    throw new IllegalArgumentException("Product quantity must be positive for product ID: " + itemDTO.getProductId());
                }
                productsInOrder.setProductQuantity(itemDTO.getProductQuantity());
                if (itemDTO.getVoucherId() != null) {
                    Voucher voucher = voucherRepository.findById(itemDTO.getVoucherId())
                            .orElse(null);
                    productsInOrder.setVoucher(voucher);
                }
                if (itemDTO.getProviderId() == null) {
                    throw new IllegalArgumentException("Provider ID cannot be null for product ID: " + itemDTO.getProductId());
                }
                productsInOrder.setProviderId(itemDTO.getProviderId());
                if (product.getProductMinPrice() == null) {
                    throw new IllegalArgumentException("Product min price is null for product ID: " + product.getProductId());
                }
                BigDecimal priceForCalculation = BigDecimal.valueOf(product.getProductMinPrice()); // CHỈNH SỬA: Chuyển đổi Integer sang BigDecimal

                order.addProductInOrder(productsInOrder);
                totalAmount = totalAmount.add(priceForCalculation.multiply(BigDecimal.valueOf(itemDTO.getProductQuantity())));
            }
        }
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDTO(savedOrder);
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

        existingOrder.getProductsInOrder().clear();
        BigDecimal updatedTotalAmount = BigDecimal.ZERO;

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getItems()) {
                com.project.nmcnpm.entity.Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + itemDTO.getProductId()));
                Shop shop = shopRepository.findById(itemDTO.getShopId())
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + itemDTO.getShopId()));

                ProductsInOrder productsInOrder = new ProductsInOrder();
                productsInOrder.setOrder(existingOrder);
                productsInOrder.setShop(shop);

                productsInOrder.setProductName(itemDTO.getProductName());
                productsInOrder.setColor(itemDTO.getColor());
                productsInOrder.setSize(itemDTO.getSize());
                productsInOrder.setProductImageUrl(itemDTO.getProductImageUrl());

                productsInOrder.setProductPrice(itemDTO.getProductPrice());

                if (itemDTO.getProductQuantity() == null || itemDTO.getProductQuantity() <= 0) {
                    throw new IllegalArgumentException("Product quantity must be positive for product ID: " + itemDTO.getProductId());
                }
                productsInOrder.setProductQuantity(itemDTO.getProductQuantity());

                if (itemDTO.getVoucherId() != null) {
                    Voucher voucher = voucherRepository.findById(itemDTO.getVoucherId())
                            .orElse(null);
                    productsInOrder.setVoucher(voucher);
                }
                if (itemDTO.getProviderId() == null) {
                    throw new IllegalArgumentException("Provider ID cannot be null for product ID: " + itemDTO.getProductId());
                }
                productsInOrder.setProviderId(itemDTO.getProviderId());

                if (product.getProductMinPrice() == null) {
                    throw new IllegalArgumentException("Product min price is null for product ID: " + product.getProductId());
                }
                BigDecimal priceForCalculation = BigDecimal.valueOf(product.getProductMinPrice()); // CHỈNH SỬA: Chuyển đổi Integer sang BigDecimal

                existingOrder.addProductInOrder(productsInOrder);
                updatedTotalAmount = updatedTotalAmount.add(priceForCalculation.multiply(BigDecimal.valueOf(itemDTO.getProductQuantity())));
            }
        }
        existingOrder.setTotalAmount(updatedTotalAmount);
        Order savedOrder = orderRepository.save(existingOrder);

        return orderRepository.findByIdWithDetails(savedOrder.getOrderId())
                .map(OrderResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Updated order not found after re-fetch: " + savedOrder.getOrderId()));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Integer id) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        return new OrderResponseDTO(order);
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
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findAllWithDetails(pageable);
        List<OrderResponseDTO> dtoList = ordersPage.getContent().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, ordersPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUserId(Integer userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Page<Order> ordersPage = orderRepository.findByUserWithDetails(user, pageable);
        List<OrderResponseDTO> dtoList = ordersPage.getContent().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, ordersPage.getTotalElements());
    }
}