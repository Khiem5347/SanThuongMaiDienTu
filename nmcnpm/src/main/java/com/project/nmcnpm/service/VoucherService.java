package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.VoucherDTO;
import com.project.nmcnpm.dto.VoucherResponseDTO;
import com.project.nmcnpm.entity.Voucher; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface VoucherService {
    Voucher createVoucher(VoucherDTO voucherDTO);
    VoucherResponseDTO getVoucherById(Integer voucherId);
    VoucherResponseDTO getVoucherByCode(String voucherCode);
    Voucher updateVoucher(Integer voucherId, VoucherDTO voucherDTO);
    void deleteVoucher(Integer voucherId);
    Page<VoucherResponseDTO> getAllVouchers(Pageable pageable);
    List<VoucherResponseDTO> getActiveVouchers(); 
    List<VoucherResponseDTO> getVouchersByDiscountType(Voucher.DiscountType discountType);
}
