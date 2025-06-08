package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.AddressRepository;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.dto.AddressDTO;
import com.project.nmcnpm.dto.AddressResponseDTO;
import com.project.nmcnpm.entity.Address;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImplementation implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public AddressServiceImplementation(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public AddressResponseDTO createAddress(AddressDTO addressDTO) {
        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + addressDTO.getUserId()));
        Address address = new Address();
        address.setUser(user);
        address.setRecipientName(addressDTO.getRecipientName());
        address.setPhone(addressDTO.getPhone());
        address.setDetailAddress(addressDTO.getDetailAddress());
        List<Address> userAddresses = addressRepository.findByUserUserId(user.getUserId());
        boolean hasExistingAddresses = !userAddresses.isEmpty();
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault()) {
            userAddresses.stream()
                    .filter(Address::getIsDefault)
                    .forEach(addr -> {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    });
            address.setIsDefault(true);
        } else if (!hasExistingAddresses) {
            address.setIsDefault(true);
        } else {
            address.setIsDefault(false);
        }
        Address savedAddress = addressRepository.save(address);
        return new AddressResponseDTO(savedAddress);
    }
    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(Integer addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));
        return new AddressResponseDTO(address);
    }
    @Override
    @Transactional
    public AddressResponseDTO updateAddress(Integer addressId, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));
        Optional.ofNullable(addressDTO.getRecipientName()).ifPresent(existingAddress::setRecipientName);
        Optional.ofNullable(addressDTO.getPhone()).ifPresent(existingAddress::setPhone);
        Optional.ofNullable(addressDTO.getDetailAddress()).ifPresent(existingAddress::setDetailAddress);
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault() && !existingAddress.getIsDefault()) {
            List<Address> userAddresses = addressRepository.findByUserUserId(existingAddress.getUser().getUserId());
            userAddresses.stream()
                    .filter(Address::getIsDefault)
                    .forEach(addr -> {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    });
            existingAddress.setIsDefault(true);
        } else if (addressDTO.getIsDefault() != null && !addressDTO.getIsDefault() && existingAddress.getIsDefault()) {
            existingAddress.setIsDefault(false);
        }
        Address updatedAddress = addressRepository.save(existingAddress);
        return new AddressResponseDTO(updatedAddress);
    }
    @Override
    @Transactional
    public void deleteAddress(Integer addressId, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        Address addressToDelete = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));
        if (!addressToDelete.getUser().getUserId().equals(userId)) {
            throw new SecurityException("Address with ID " + addressId + " does not belong to user with ID " + userId);
        }
        List<Address> userAddresses = addressRepository.findByUserUserId(userId); 
        if (addressToDelete.getIsDefault()) {
            Optional<Address> newDefaultCandidate = userAddresses.stream()
                    .filter(addr -> !addr.getAddressId().equals(addressId))
                    .findFirst();
            if (newDefaultCandidate.isPresent()) {
                newDefaultCandidate.get().setIsDefault(true);
                addressRepository.save(newDefaultCandidate.get());
            } else {
            }
        }
        addressRepository.deleteById(addressId);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<AddressResponseDTO> getAllAddresses(Pageable pageable) {
        Page<Address> addressesPage = addressRepository.findAll(pageable);
        return addressesPage.map(AddressResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAddressesByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        List<Address> addresses = addressRepository.findByUserUserId(userId);
        return addresses.stream()
                .map(AddressResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getDefaultAddressByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Address defaultAddress = addressRepository.findByUserUserIdAndIsDefaultTrue(userId);
        if (defaultAddress == null) {
            throw new EntityNotFoundException("No default address found for user with id: " + userId);
        }
        return new AddressResponseDTO(defaultAddress);
    }
    @Override
    @Transactional
    public AddressResponseDTO setDefaultAddress(Integer userId, Integer addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Address addressToSetDefault = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        if (!addressToSetDefault.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Address with ID " + addressId + " does not belong to user with ID " + userId);
        }
        List<Address> userAddresses = addressRepository.findByUserUserId(userId);
        userAddresses.stream()
                .filter(Address::getIsDefault)
                .forEach(addr -> {
                    addr.setIsDefault(false);
                    addressRepository.save(addr);
                });
        addressToSetDefault.setIsDefault(true);
        Address updatedAddress = addressRepository.save(addressToSetDefault);
        return new AddressResponseDTO(updatedAddress);
    }
}