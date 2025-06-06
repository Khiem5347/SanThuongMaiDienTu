package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ShippingLinks")
public class ShippingLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Integer linkId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private ShippingProvider shippingProvider;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    public ShippingLink() {
    }
    public ShippingLink(Integer linkId, ShippingProvider shippingProvider, Product product) {
        this.linkId = linkId;
        this.shippingProvider = shippingProvider;
        this.product = product;
    }
    public Integer getLinkId() {
        return linkId;
    }
    public ShippingProvider getShippingProvider() {
        return shippingProvider;
    }
    public Product getProduct() {
        return product;
    }
    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }
    public void setShippingProvider(ShippingProvider shippingProvider) {
        this.shippingProvider = shippingProvider;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}