package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.Voucher;
import java.math.BigDecimal;
import java.sql.Date; 

public class VoucherResponseDTO {
    private Integer voucherId;
    private String voucherCode;
    private String voucherDescription;
    private Voucher.DiscountType discountType;
    private BigDecimal discountValue;
    private Integer minPrice;
    private Date startDate;
    private Date endDate;
    private Integer maxUsage;
    private Integer usageCount;
    private String targetUserRole;
    public VoucherResponseDTO() {
    }
    public VoucherResponseDTO(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherCode = voucher.getVoucherCode();
        this.voucherDescription = voucher.getVoucherDescription();
        this.discountType = voucher.getDiscountType();
        this.discountValue = voucher.getDiscountValue();
        this.minPrice = voucher.getMinPrice();
        this.startDate = voucher.getStartDate();
        this.endDate = voucher.getEndDate();
        this.maxUsage = voucher.getMaxUsage();
        this.usageCount = voucher.getUsageCount();
        this.targetUserRole = voucher.getTargetUserRole();
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
    public String getVoucherDescription() {
        return voucherDescription;
    }
    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }
    public Voucher.DiscountType getDiscountType() {
        return discountType;
    }
    public void setDiscountType(Voucher.DiscountType discountType) {
        this.discountType = discountType;
    }
    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Integer getMaxUsage() {
        return maxUsage;
    }
    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }
    public Integer getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
    public String getTargetUserRole() {
        return targetUserRole;
    }
    public void setTargetUserRole(String targetUserRole) {
        this.targetUserRole = targetUserRole;
    }
}
