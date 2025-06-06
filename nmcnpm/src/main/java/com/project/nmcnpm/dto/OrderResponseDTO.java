package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.ProductsInOrder;
import java.math.BigDecimal; 
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDTO {
    private Integer orderId;
    private Integer userId;
    private String userName;
    private Integer addressId;
    private BigDecimal totalAmount; 
    private List<OrderItemResponseDTO> items;
    private Integer totalItemsQuantity;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        User user = order.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername();
        }
        Address address = order.getAddress();
        if (address != null) {
            this.addressId = address.getAddressId();
        }
        this.totalAmount = order.getTotalAmount();

        if (order.getProductsInOrder() != null && !order.getProductsInOrder().isEmpty()) {
            this.items = order.getProductsInOrder().stream()
                    .map(OrderItemResponseDTO::new)
                    .collect(Collectors.toList());
            this.totalItemsQuantity = this.items.stream()
                    .mapToInt(OrderItemResponseDTO::getProductQuantity)
                    .sum();
        } else {
            this.items = List.of();
            this.totalItemsQuantity = 0;
        }
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<OrderItemResponseDTO> getItems() { return items; }
    public void setItems(List<OrderItemResponseDTO> items) { this.items = items; }
    public Integer getTotalItemsQuantity() { return totalItemsQuantity; }
    public void setTotalItemsQuantity(Integer totalItemsQuantity) { this.totalItemsQuantity = totalItemsQuantity; }

    public static class OrderItemResponseDTO {
        private Integer productsInOrderId;
        private String productName;
        private Integer shopId;
        private String shopName;
        private String color;
        private String size;
        private String productImageUrl;
        private Integer productPrice; 
        private Integer productQuantity;
        private Integer voucherId; 
        private Integer providerId; 
        public OrderItemResponseDTO() {
        }

        public OrderItemResponseDTO(ProductsInOrder productsInOrder) {
            this.productsInOrderId = productsInOrder.getProductsInOrderId();
            this.productName = productsInOrder.getProductName();
            if (productsInOrder.getShop() != null) {
                this.shopId = productsInOrder.getShop().getShopId();
                this.shopName = productsInOrder.getShop().getShopName();
            }
            this.color = productsInOrder.getColor();
            this.size = productsInOrder.getSize();
            this.productImageUrl = productsInOrder.getProductImageUrl();
            this.productPrice = productsInOrder.getProductPrice(); 
            this.productQuantity = productsInOrder.getProductQuantity();
            if (productsInOrder.getVoucher() != null) {
                this.voucherId = productsInOrder.getVoucher().getVoucherId();
            } else {
                this.voucherId = null; 
            }
            this.providerId = productsInOrder.getProviderId();
        }

        public Integer getProductsInOrderId() { return productsInOrderId; }
        public void setProductsInOrderId(Integer productsInOrderId) { this.productsInOrderId = productsInOrderId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getShopId() { return shopId; }
        public void setShopId(Integer shopId) { this.shopId = shopId; }
        public String getShopName() { return shopName; }
        public void setShopName(String shopName) { this.shopName = shopName; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
        public Integer getProductPrice() { return productPrice; } // ĐÃ ĐỔI
        public void setProductPrice(Integer productPrice) { this.productPrice = productPrice; } // ĐÃ ĐỔI
        public Integer getProductQuantity() { return productQuantity; }
        public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }
        public Integer getVoucherId() { return voucherId; }
        public void setVoucherId(Integer voucherId) { this.voucherId = voucherId; }
        public Integer getProviderId() { return providerId; }
        public void setProviderId(Integer providerId) { this.providerId = providerId; }
    }
}