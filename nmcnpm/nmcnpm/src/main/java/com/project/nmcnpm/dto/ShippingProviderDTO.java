package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShippingProviderDTO {
    @NotBlank(message = "Provider name cannot be blank")
    @Size(max = 100, message = "Provider name cannot exceed 100 characters")
    private String providerName;
    @Size(max = 20, message = "Contact phone cannot exceed 20 characters")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact phone number must be 10-15 digits",
             groups = {jakarta.validation.groups.Default.class})
    private String contactPhone;
    public ShippingProviderDTO() {
    }
    public ShippingProviderDTO(String providerName, String contactPhone) {
        this.providerName = providerName;
        this.contactPhone = contactPhone;
    }
    public String getProviderName() {
        return providerName;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
