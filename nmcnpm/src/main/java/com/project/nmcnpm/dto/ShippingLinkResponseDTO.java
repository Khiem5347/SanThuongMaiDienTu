package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.ShippingLink;
import com.project.nmcnpm.entity.ShippingProvider; 
import com.project.nmcnpm.entity.Product;          

public class ShippingLinkResponseDTO {
    private Integer linkId;
    private Integer providerId;
    private String providerName; 
    private Integer productId;
    private String productName;  
    public ShippingLinkResponseDTO() {
    }
    public ShippingLinkResponseDTO(ShippingLink shippingLink) {
        this.linkId = shippingLink.getLinkId();
        ShippingProvider provider = shippingLink.getShippingProvider();
        if (provider != null) {
            this.providerId = provider.getProviderId();
            this.providerName = provider.getProviderName();
        }
        Product product = shippingLink.getProduct();
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName(); 
        }
    }
    public Integer getLinkId() {
        return linkId;
    }
    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
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
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
