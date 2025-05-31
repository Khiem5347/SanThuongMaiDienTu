package com.project.nmcnpm.dto;

import jakarta.validation.constraints.NotNull;

public class AppliedVoucherDTO {
    @NotNull(message = "Category ID cannot be null")
    private Integer categoryId;
    @NotNull(message = "Voucher ID cannot be null")
    private Integer voucherId;
    public AppliedVoucherDTO() {
    }
    public AppliedVoucherDTO(Integer categoryId, Integer voucherId) {
        this.categoryId = categoryId;
        this.voucherId = voucherId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public Integer getVoucherId() {
        return voucherId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }
}
