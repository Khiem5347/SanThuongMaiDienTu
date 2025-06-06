package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ShippingService;
import com.project.nmcnpm.entity.ShippingProvider; 

public class ShippingServiceResponseDTO {
    private Integer serviceId;
    private Integer providerId;
    private String providerName; 
    private Integer fastPrice;
    private Integer defaultPrice;
    private Integer addDistance;
    public ShippingServiceResponseDTO() {
    }
    public ShippingServiceResponseDTO(ShippingService shippingService) {
        this.serviceId = shippingService.getServiceId();
        this.fastPrice = shippingService.getFastPrice();
        this.defaultPrice = shippingService.getDefaultPrice();
        this.addDistance = shippingService.getAddDistance();
        ShippingProvider provider = shippingService.getShippingProvider();
        if (provider != null) {
            this.providerId = provider.getProviderId();
            this.providerName = provider.getProviderName();
        }
    }
    public Integer getServiceId() {
        return serviceId;
    }
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
    public Integer getFastPrice() {
        return fastPrice;
    }
    public void setFastPrice(Integer fastPrice) {
        this.fastPrice = fastPrice;
    }
    public Integer getDefaultPrice() {
        return defaultPrice;
    }
    public void setDefaultPrice(Integer defaultPrice) {
        this.defaultPrice = defaultPrice;
    }
    public Integer getAddDistance() {
        return addDistance;
    }
    public void setAddDistance(Integer addDistance) {
        this.addDistance = addDistance;
    }
}
