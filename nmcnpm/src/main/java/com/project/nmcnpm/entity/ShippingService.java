package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ShippingServices")
public class ShippingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private ShippingProvider shippingProvider;
    @Column(name = "fast_price", nullable = false)
    private Integer fastPrice;
    @Column(name = "default_price", nullable = false)
    private Integer defaultPrice;
    @Column(name = "add_distance", nullable = false)
    private Integer addDistance;
    public ShippingService() {
    }
    public ShippingService(Integer serviceId, ShippingProvider shippingProvider, Integer fastPrice, Integer defaultPrice, Integer addDistance) {
        this.serviceId = serviceId;
        this.shippingProvider = shippingProvider;
        this.fastPrice = fastPrice;
        this.defaultPrice = defaultPrice;
        this.addDistance = addDistance;
    }
    public Integer getServiceId() {
        return serviceId;
    }
    public ShippingProvider getShippingProvider() {
        return shippingProvider;
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
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public void setShippingProvider(ShippingProvider shippingProvider) {
        this.shippingProvider = shippingProvider;
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