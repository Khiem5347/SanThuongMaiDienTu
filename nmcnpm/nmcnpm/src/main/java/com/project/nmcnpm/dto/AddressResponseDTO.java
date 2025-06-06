package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.User; 

public class AddressResponseDTO {
    private Integer addressId;
    private Integer userId;
    private String userName; 
    private String recipientName;
    private String phone;
    private String detailAddress;
    private Boolean isDefault;
    public AddressResponseDTO() {
    }
    public AddressResponseDTO(Address address) {
        this.addressId = address.getAddressId();
        this.recipientName = address.getRecipientName();
        this.phone = address.getPhone();
        this.detailAddress = address.getDetailAddress();
        this.isDefault = address.getIsDefault();
        User user = address.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUsername(); 
        }
    }
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDetailAddress() {
        return detailAddress;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
