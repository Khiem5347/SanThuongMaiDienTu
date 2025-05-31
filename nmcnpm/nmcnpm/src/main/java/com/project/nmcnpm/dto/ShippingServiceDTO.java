package com.project.nmcnpm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ShippingServiceDTO {
    @NotNull(message = "Provider ID cannot be null")
    private Integer providerId;
    @NotNull(message = "Fast price cannot be null")
    @Min(value = 0, message = "Fast price must be non-negative")
    private Integer fastPrice;
    @NotNull(message = "Default price cannot be null")
    @Min(value = 0, message = "Default price must be non-negative")
    private Integer defaultPrice;
    @NotNull(message = "Add distance cannot be null")
    @Min(value = 0, message = "Add distance must be non-negative")
    private Integer addDistance;
    public ShippingServiceDTO() {
    }
    public ShippingServiceDTO(Integer providerId, Integer fastPrice, Integer defaultPrice, Integer addDistance) {
        this.providerId = providerId;
        this.fastPrice = fastPrice;
        this.defaultPrice = defaultPrice;
        this.addDistance = addDistance;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public Integer getFastPrice() {
        return fastPrice;
    }
    public Integer getDefaultPrice() {
        return defaultPrice;
    }
    public Integer getAddDistance() {
        return addDistance;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public void setFastPrice(Integer fastPrice) {
        this.fastPrice = fastPrice;
    }
    public void setDefaultPrice(Integer defaultPrice) {
        this.defaultPrice = defaultPrice;
    }
    public void setAddDistance(Integer addDistance) {
        this.addDistance = addDistance;
    }
}
