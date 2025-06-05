package com.project.nmcnpm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AppliedVouchers")
public class AppliedVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applied_id")
    private Integer appliedId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;
    public AppliedVoucher() {
    }
    public AppliedVoucher(Integer appliedId, Category category, Voucher voucher) {
        this.appliedId = appliedId;
        this.category = category;
        this.voucher = voucher;
    }
    public Integer getAppliedId() {
        return appliedId;
    }
    public Category getCategory() {
        return category;
    }
    public Voucher getVoucher() {
        return voucher;
    }
    public void setAppliedId(Integer appliedId) {
        this.appliedId = appliedId;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}