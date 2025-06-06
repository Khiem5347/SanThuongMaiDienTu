package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ShippingProvider;
import java.util.List;
import java.util.stream.Collectors;

public class ShippingProviderResponseDTO {
    private Integer providerId;
    private String providerName;
    private String contactPhone;
    private int shippingLinkCount;
    private int shippingServiceCount;
    private int productsInOrderCount;
    public ShippingProviderResponseDTO() {
    }
    public ShippingProviderResponseDTO(ShippingProvider shippingProvider) {
        this.providerId = shippingProvider.getProviderId();
        this.providerName = shippingProvider.getProviderName();
        this.contactPhone = shippingProvider.getContactPhone();
        this.shippingLinkCount = shippingProvider.getShippingLinks() != null ? shippingProvider.getShippingLinks().size() : 0;
        this.shippingServiceCount = shippingProvider.getShippingServices() != null ? shippingProvider.getShippingServices().size() : 0;
        this.productsInOrderCount = shippingProvider.getProductsInOrders() != null ? shippingProvider.getProductsInOrders().size() : 0;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public int getShippingLinkCount() {
        return shippingLinkCount;
    }
    public void setShippingLinkCount(int shippingLinkCount) {
        this.shippingLinkCount = shippingLinkCount;
    }
    public int getShippingServiceCount() {
        return shippingServiceCount;
    }
    public void setShippingServiceCount(int shippingServiceCount) {
        this.shippingServiceCount = shippingServiceCount;
    }
    public int getProductsInOrderCount() {
        return productsInOrderCount;
    }
    public void setProductsInOrderCount(int productsInOrderCount) {
        this.productsInOrderCount = productsInOrderCount;
    }
}
