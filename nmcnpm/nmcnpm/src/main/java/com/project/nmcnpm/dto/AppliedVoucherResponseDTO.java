package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.AppliedVoucher;
import com.project.nmcnpm.entity.Category; 
import com.project.nmcnpm.entity.Voucher;  

public class AppliedVoucherResponseDTO {
    private Integer appliedId;
    private Integer categoryId;
    private String categoryName; 
    private Integer voucherId;
    private String voucherCode; 
    public AppliedVoucherResponseDTO() {
    }
    public AppliedVoucherResponseDTO(AppliedVoucher appliedVoucher) {
        this.appliedId = appliedVoucher.getAppliedId();
        Category category = appliedVoucher.getCategory();
        if (category != null) {
            this.categoryId = category.getCategoryId();
            this.categoryName = category.getCategoryName();
        }
        Voucher voucher = appliedVoucher.getVoucher();
        if (voucher != null) {
            this.voucherId = voucher.getVoucherId();
            this.voucherCode = voucher.getVoucherCode();
        }
    }
    public Integer getAppliedId() {
        return appliedId;
    }
    public void setAppliedId(Integer appliedId) {
        this.appliedId = appliedId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getVoucherId() {
        return voucherId;
    }
    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }
    public String getVoucherCode() {
        return voucherCode;
    }
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
