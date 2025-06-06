package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.AppliedVoucherDTO;
import com.project.nmcnpm.dto.AppliedVoucherResponseDTO; 
import com.project.nmcnpm.entity.AppliedVoucher; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AppliedVoucherService {
    AppliedVoucherResponseDTO createAppliedVoucher(AppliedVoucherDTO appliedVoucherDTO);
    AppliedVoucherResponseDTO getAppliedVoucherById(Integer appliedId);
    void deleteAppliedVoucher(Integer appliedId);
    Page<AppliedVoucherResponseDTO> getAllAppliedVouchers(Pageable pageable);
    List<AppliedVoucherResponseDTO> getAppliedVouchersByCategoryId(Integer categoryId);
    List<AppliedVoucherResponseDTO> getAppliedVouchersByVoucherId(Integer voucherId);
}