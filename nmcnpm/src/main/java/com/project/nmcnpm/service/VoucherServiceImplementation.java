package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.VoucherRepository;
import com.project.nmcnpm.dto.VoucherDTO;
import com.project.nmcnpm.dto.VoucherResponseDTO;
import com.project.nmcnpm.entity.Voucher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.sql.Date; 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImplementation implements VoucherService {
    private final VoucherRepository voucherRepository;
    public VoucherServiceImplementation(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    @Override
    @Transactional
    public Voucher createVoucher(VoucherDTO voucherDTO) {
        if (voucherRepository.findByVoucherCode(voucherDTO.getVoucherCode()).isPresent()) {
            throw new IllegalArgumentException("Voucher with code '" + voucherDTO.getVoucherCode() + "' already exists.");
        }
        Voucher voucher = new Voucher();
        voucher.setVoucherCode(voucherDTO.getVoucherCode());
        voucher.setVoucherDescription(voucherDTO.getVoucherDescription());
        voucher.setDiscountType(voucherDTO.getDiscountType());
        voucher.setDiscountValue(voucherDTO.getDiscountValue());
        voucher.setMinPrice(voucherDTO.getMinPrice());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setEndDate(voucherDTO.getEndDate());
        voucher.setMaxUsage(voucherDTO.getMaxUsage());
        voucher.setUsageCount(0); 
        voucher.setTargetUserRole(voucherDTO.getTargetUserRole());
        return voucherRepository.save(voucher);
    }
    @Override
    @Transactional(readOnly = true)
    public VoucherResponseDTO getVoucherById(Integer voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new EntityNotFoundException("Voucher not found with id: " + voucherId));
        return new VoucherResponseDTO(voucher);
    }
    @Override
    @Transactional(readOnly = true)
    public VoucherResponseDTO getVoucherByCode(String voucherCode) {
        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode)
                .orElseThrow(() -> new EntityNotFoundException("Voucher not found with code: " + voucherCode));
        return new VoucherResponseDTO(voucher);
    }
    @Override
    @Transactional
    public Voucher updateVoucher(Integer voucherId, VoucherDTO voucherDTO) {
        Voucher existingVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new EntityNotFoundException("Voucher not found with id: " + voucherId));
        if (voucherDTO.getVoucherCode() != null && !voucherDTO.getVoucherCode().equals(existingVoucher.getVoucherCode())) {
            if (voucherRepository.findByVoucherCode(voucherDTO.getVoucherCode()).isPresent()) {
                throw new IllegalArgumentException("Voucher with code '" + voucherDTO.getVoucherCode() + "' already exists.");
            }
            existingVoucher.setVoucherCode(voucherDTO.getVoucherCode());
        }
        if (voucherDTO.getVoucherDescription() != null) {
            existingVoucher.setVoucherDescription(voucherDTO.getVoucherDescription());
        }
        if (voucherDTO.getDiscountType() != null) {
            existingVoucher.setDiscountType(voucherDTO.getDiscountType());
        }
        if (voucherDTO.getDiscountValue() != null) {
            existingVoucher.setDiscountValue(voucherDTO.getDiscountValue());
        }
        if (voucherDTO.getMinPrice() != null) {
            existingVoucher.setMinPrice(voucherDTO.getMinPrice());
        }
        if (voucherDTO.getStartDate() != null) {
            existingVoucher.setStartDate(voucherDTO.getStartDate());
        }
        if (voucherDTO.getEndDate() != null) {
            existingVoucher.setEndDate(voucherDTO.getEndDate());
        }
        if (voucherDTO.getMaxUsage() != null) {
            existingVoucher.setMaxUsage(voucherDTO.getMaxUsage());
        }
        if (voucherDTO.getTargetUserRole() != null) {
            existingVoucher.setTargetUserRole(voucherDTO.getTargetUserRole());
        }
        return voucherRepository.save(existingVoucher);
    }
    @Override
    @Transactional
    public void deleteVoucher(Integer voucherId) {
        if (!voucherRepository.existsById(voucherId)) {
            throw new EntityNotFoundException("Voucher not found with id: " + voucherId);
        }
        voucherRepository.deleteById(voucherId);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<VoucherResponseDTO> getAllVouchers(Pageable pageable) {
        Page<Voucher> vouchersPage = voucherRepository.findAll(pageable);
        return vouchersPage.map(VoucherResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> getActiveVouchers() {
        Date currentDate = Date.valueOf(LocalDate.now());
        List<Voucher> activeVouchers = voucherRepository.findAll().stream()
                .filter(voucher -> (voucher.getStartDate() == null || !voucher.getStartDate().after(currentDate)) &&
                                   (voucher.getEndDate() == null || !voucher.getEndDate().before(currentDate)) &&
                                   (voucher.getMaxUsage() == null || voucher.getUsageCount() < voucher.getMaxUsage()))
                .collect(Collectors.toList());
        return activeVouchers.stream()
                .map(VoucherResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> getVouchersByDiscountType(Voucher.DiscountType discountType) {
        List<Voucher> vouchers = voucherRepository.findAll().stream()
                .filter(voucher -> voucher.getDiscountType().equals(discountType))
                .collect(Collectors.toList());
        return vouchers.stream()
                .map(VoucherResponseDTO::new)
                .collect(Collectors.toList());
    }
}
