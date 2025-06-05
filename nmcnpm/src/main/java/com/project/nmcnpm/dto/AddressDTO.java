package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    @NotBlank(message = "Recipient name cannot be blank")
    @Size(max = 100, message = "Recipient name cannot exceed 100 characters")
    private String recipientName;
    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits") 
    private String phone;
    @NotBlank(message = "Detail address cannot be blank")
    @Size(max = 255, message = "Detail address cannot exceed 255 characters")
    private String detailAddress;
    private Boolean isDefault = false;
    public AddressDTO() {
    }
    public AddressDTO(Integer userId, String recipientName, String phone, String detailAddress, Boolean isDefault) {
        this.userId = userId;
        this.recipientName = recipientName;
        this.phone = phone;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public String getPhone() {
        return phone;
    }
    public String getDetailAddress() {
        return detailAddress;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
