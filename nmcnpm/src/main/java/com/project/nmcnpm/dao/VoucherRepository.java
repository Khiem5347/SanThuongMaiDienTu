package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate; 
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Optional<Voucher> findByVoucherCode(String voucherCode);
    @Query("SELECT v FROM Voucher v WHERE " +
            "(v.startDate IS NULL OR v.startDate <= :currentDate) AND " +
            "(v.endDate IS NULL OR v.endDate >= :currentDate) AND " +
            "(v.maxUsage IS NULL OR v.usageCount < v.maxUsage)")
    List<Voucher> findActiveVouchers(@Param("currentDate") LocalDate currentDate);
    List<Voucher> findByDiscountType(Voucher.DiscountType discountType);
}