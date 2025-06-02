package com.project.nmcnpm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet; 
import java.util.Set;

@Entity
@Table(name = "Vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Integer voucherId;
    @Column(name = "voucher_code", unique = true, nullable = false, length = 50)
    private String voucherCode;
    @Column(name = "voucher_description", columnDefinition = "TEXT")
    private String voucherDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;
    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;
    @Column(name = "min_price", nullable = false)
    private Integer minPrice;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "max_usage")
    private Integer maxUsage;
    @Column(name = "usage_count")
    private Integer usageCount = 0;
    @Column(name = "target_user_role", columnDefinition = "SET('buyer', 'seller', 'all') DEFAULT 'all'")
    private String targetUserRole;
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppliedVoucher> appliedVouchers = new HashSet<>(); 
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductsInOrder> productsInOrders = new HashSet<>(); 
    public enum DiscountType {
        percentage, fixed
    }
    public Voucher() {
    }
    public Voucher(Integer voucherId, String voucherCode, String voucherDescription, DiscountType discountType,
                   BigDecimal discountValue, Integer minPrice, Date startDate, Date endDate, Integer maxUsage,
                   Integer usageCount, String targetUserRole, Set<AppliedVoucher> appliedVouchers,
                   Set<ProductsInOrder> productsInOrders) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherDescription = voucherDescription;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxUsage = maxUsage;
        this.usageCount = usageCount;
        this.targetUserRole = targetUserRole;
        this.appliedVouchers = appliedVouchers;
        this.productsInOrders = productsInOrders;
    }
    public Integer getVoucherId() {
        return voucherId;
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
    public Integer getUsageCount() {
        return usageCount;
    }
    public String getTargetUserRole() {
        return targetUserRole;
    }
    public Set<AppliedVoucher> getAppliedVouchers() {
        return appliedVouchers;
    }
    public Set<ProductsInOrder> getProductsInOrders() {
        return productsInOrders;
    }
    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
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
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
    public void setTargetUserRole(String targetUserRole) {
        this.targetUserRole = targetUserRole;
    }
    public void setAppliedVouchers(Set<AppliedVoucher> appliedVouchers) {
        this.appliedVouchers = appliedVouchers;
    }
    public void setProductsInOrders(Set<ProductsInOrder> productsInOrders) {
        this.productsInOrders = productsInOrders;
    }
    public void addAppliedVoucher(AppliedVoucher appliedVoucher) {
        if (appliedVoucher != null) {
            if (this.appliedVouchers == null) {
                this.appliedVouchers = new HashSet<>();
            }
            this.appliedVouchers.add(appliedVoucher);
            appliedVoucher.setVoucher(this); 
        }
    }
    public void removeAppliedVoucher(AppliedVoucher appliedVoucher) {
        if (appliedVoucher != null && this.appliedVouchers != null) {
            this.appliedVouchers.remove(appliedVoucher);
            appliedVoucher.setVoucher(null);
        }
    }
    public void addProductsInOrder(ProductsInOrder productsInOrder) {
        if (productsInOrder != null) {
            if (this.productsInOrders == null) {
                this.productsInOrders = new HashSet<>();
            }
            this.productsInOrders.add(productsInOrder);
            productsInOrder.setVoucher(this); 
        }
    }
    public void removeProductsInOrder(ProductsInOrder productsInOrder) {
        if (productsInOrder != null && this.productsInOrders != null) {
            this.productsInOrders.remove(productsInOrder);
            productsInOrder.setVoucher(null);
        }
    }
}