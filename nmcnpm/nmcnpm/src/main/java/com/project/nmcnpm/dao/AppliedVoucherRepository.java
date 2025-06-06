package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.AppliedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppliedVoucherRepository extends JpaRepository<AppliedVoucher, Integer> {
    List<AppliedVoucher> findByCategoryCategoryId(Integer categoryId);
    List<AppliedVoucher> findByVoucherVoucherId(Integer voucherId);
}