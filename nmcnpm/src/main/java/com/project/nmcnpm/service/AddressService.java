package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.AddressDTO;
import com.project.nmcnpm.dto.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AddressService {
    AddressResponseDTO createAddress(AddressDTO addressDTO);
    AddressResponseDTO getAddressById(Integer addressId);
    AddressResponseDTO updateAddress(Integer addressId, AddressDTO addressDTO);
    void deleteAddress(Integer addressId, Integer userId);
    Page<AddressResponseDTO> getAllAddresses(Pageable pageable);
    List<AddressResponseDTO> getAddressesByUserId(Integer userId);
    AddressResponseDTO getDefaultAddressByUserId(Integer userId);
    AddressResponseDTO setDefaultAddress(Integer userId, Integer addressId);
}