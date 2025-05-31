package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Voucher.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern; 
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;

public class VoucherDTO {
    @NotBlank(message = "Voucher code cannot be blank")
    @Size(max = 50, message = "Voucher code cannot exceed 50 characters")
    private String voucherCode;
    private String voucherDescription;
    @NotNull(message = "Discount type cannot be null")
    private DiscountType discountType;
    @NotNull(message = "Discount value cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
    @NotNull(message = "Min price cannot be null")
    @Min(value = 0, message = "Min price must be non-negative")
    private Integer minPrice;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;
    @Min(value = 0, message = "Max usage must be non-negative")
    private Integer maxUsage;
    @NotBlank(message = "Target user role cannot be blank")
    @Pattern(regexp = "buyer|seller|all", message = "Target user role must be 'buyer', 'seller', or 'all'")
    private String targetUserRole;
    public VoucherDTO() {
    }
    public VoucherDTO(String voucherCode, String voucherDescription, DiscountType discountType, BigDecimal discountValue, Integer minPrice, Date startDate, Date endDate, Integer maxUsage, String targetUserRole) {
        this.voucherCode = voucherCode;
        this.voucherDescription = voucherDescription;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxUsage = maxUsage;
        this.targetUserRole = targetUserRole;
    }
    public String getVoucherCode() {
        return voucherCode;
    }
    public String getVoucherDescription() {
        return voucherDescription;
    }
    public DiscountType getDiscountType() {
        return discountType;
    }
    public BigDecimal getDiscountValue() {
        return discountValue;
    }
    public Integer getMinPrice() {
        return minPrice;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public Integer getMaxUsage() {
        return maxUsage;
    }
    public String getTargetUserRole() {
        return targetUserRole;
    }
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }
    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }
    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }
    public void setTargetUserRole(String targetUserRole) {
        this.targetUserRole = targetUserRole;
    }
}
