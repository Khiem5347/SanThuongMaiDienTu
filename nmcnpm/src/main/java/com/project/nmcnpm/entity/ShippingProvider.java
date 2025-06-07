package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "ShippingProviders")
public class ShippingProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Integer providerId;
    @Column(name = "provider_name", nullable = false, length = 100)
    private String providerName;
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;
    @OneToMany(mappedBy = "shippingProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShippingLink> shippingLinks = new HashSet<>(); 
    @OneToMany(mappedBy = "shippingProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShippingService> shippingServices = new HashSet<>(); 
    @OneToMany(mappedBy = "shippingProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrders = new HashSet<>(); 
    public ShippingProvider() {
    }
    public ShippingProvider(Integer providerId, String providerName, String contactPhone,
                            Set<ShippingLink> shippingLinks, Set<ShippingService> shippingServices,
                            Set<ProductsInOrder> productsInOrders) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.contactPhone = contactPhone;
        this.shippingLinks = shippingLinks;
        this.shippingServices = shippingServices;
        this.productsInOrders = productsInOrders;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public String getProviderName() {
        return providerName;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public Set<ShippingLink> getShippingLinks() {
        return shippingLinks;
    }
    public Set<ShippingService> getShippingServices() {
        return shippingServices;
    }
    public Set<ProductsInOrder> getProductsInOrders() {
        return productsInOrders;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public void setShippingLinks(Set<ShippingLink> shippingLinks) {
        this.shippingLinks = shippingLinks;
    }
    public void setShippingServices(Set<ShippingService> shippingServices) {
        this.shippingServices = shippingServices;
    }
    public void setProductsInOrders(Set<ProductsInOrder> productsInOrders) {
        this.productsInOrders = productsInOrders;
    }
    public void addShippingLink(ShippingLink shippingLink) {
        if (shippingLink != null) {
            if (this.shippingLinks == null) {
                this.shippingLinks = new HashSet<>();
            }
            this.shippingLinks.add(shippingLink);
            shippingLink.setShippingProvider(this); 
        }
    }
    public void removeShippingLink(ShippingLink shippingLink) {
        if (shippingLink != null && this.shippingLinks != null) {
            this.shippingLinks.remove(shippingLink);
            shippingLink.setShippingProvider(null);
        }
    }
    public void addShippingService(ShippingService shippingService) {
        if (shippingService != null) {
            if (this.shippingServices == null) {
                this.shippingServices = new HashSet<>();
            }
            this.shippingServices.add(shippingService);
            shippingService.setShippingProvider(this); 
        }
    }
    public void removeShippingService(ShippingService shippingService) {
        if (shippingService != null && this.shippingServices != null) {
            this.shippingServices.remove(shippingService);
            shippingService.setShippingProvider(null);
        }
    }
    public void addProductsInOrder(ProductsInOrder productsInOrder) {
        if (productsInOrder != null) {
            if (this.productsInOrders == null) {
                this.productsInOrders = new HashSet<>();
            }
            this.productsInOrders.add(productsInOrder);
            productsInOrder.setShippingProvider(this); 
        }
    }
    public void removeProductsInOrder(ProductsInOrder productsInOrder) {
        if (productsInOrder != null && this.productsInOrders != null) {
            this.productsInOrders.remove(productsInOrder);
            productsInOrder.setShippingProvider(null);
        }
    }
}