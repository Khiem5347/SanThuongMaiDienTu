package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.AppliedVoucherRepository;
import com.project.nmcnpm.dao.CategoryRepository; 
import com.project.nmcnpm.dao.VoucherRepository;
import com.project.nmcnpm.dto.AppliedVoucherDTO;
import com.project.nmcnpm.dto.AppliedVoucherResponseDTO;
import com.project.nmcnpm.entity.AppliedVoucher;
import com.project.nmcnpm.entity.Category;
import com.project.nmcnpm.entity.Voucher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppliedVoucherServiceImplementation implements AppliedVoucherService {
    private final AppliedVoucherRepository appliedVoucherRepository;
    private final CategoryRepository categoryRepository;
    private final VoucherRepository voucherRepository;

    public AppliedVoucherServiceImplementation(AppliedVoucherRepository appliedVoucherRepository,
                                                CategoryRepository categoryRepository,
                                                VoucherRepository voucherRepository) {
        this.appliedVoucherRepository = appliedVoucherRepository;
        this.categoryRepository = categoryRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    @Transactional
    public AppliedVoucherResponseDTO createAppliedVoucher(AppliedVoucherDTO appliedVoucherDTO) { 
        Category category = categoryRepository.findById(appliedVoucherDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + appliedVoucherDTO.getCategoryId()));
        Voucher voucher = voucherRepository.findById(appliedVoucherDTO.getVoucherId())
                .orElseThrow(() -> new EntityNotFoundException("Voucher not found with id: " + appliedVoucherDTO.getVoucherId()));

        AppliedVoucher appliedVoucher = new AppliedVoucher();
        appliedVoucher.setCategory(category);
        appliedVoucher.setVoucher(voucher);
        
        AppliedVoucher savedAppliedVoucher = appliedVoucherRepository.save(appliedVoucher); 
        return new AppliedVoucherResponseDTO(savedAppliedVoucher); 
    }

    @Override
    @Transactional(readOnly = true)
    public AppliedVoucherResponseDTO getAppliedVoucherById(Integer appliedId) {
        AppliedVoucher appliedVoucher = appliedVoucherRepository.findById(appliedId)
                .orElseThrow(() -> new EntityNotFoundException("Applied Voucher not found with id: " + appliedId));
        return new AppliedVoucherResponseDTO(appliedVoucher);
    }

    @Override
    @Transactional
    public void deleteAppliedVoucher(Integer appliedId) {
        if (!appliedVoucherRepository.existsById(appliedId)) {
            throw new EntityNotFoundException("Applied Voucher not found with id: " + appliedId);
        }
        appliedVoucherRepository.deleteById(appliedId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppliedVoucherResponseDTO> getAllAppliedVouchers(Pageable pageable) {
        Page<AppliedVoucher> appliedVouchersPage = appliedVoucherRepository.findAll(pageable);
        return appliedVouchersPage.map(AppliedVoucherResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedVoucherResponseDTO> getAppliedVouchersByCategoryId(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
        List<AppliedVoucher> appliedVouchers = appliedVoucherRepository.findByCategoryCategoryId(categoryId);
        return appliedVouchers.stream()
                .map(AppliedVoucherResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedVoucherResponseDTO> getAppliedVouchersByVoucherId(Integer voucherId) { 
        if (!voucherRepository.existsById(voucherId)) {
            throw new EntityNotFoundException("Voucher not found with id: " + voucherId);
        }
        List<AppliedVoucher> appliedVouchers = appliedVoucherRepository.findByVoucherVoucherId(voucherId);
        return appliedVouchers.stream()
                .map(AppliedVoucherResponseDTO::new)
                .collect(Collectors.toList());
    }
}