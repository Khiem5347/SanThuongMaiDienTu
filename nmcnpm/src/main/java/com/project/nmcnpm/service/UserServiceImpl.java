package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.dto.UserResponseDTO; 
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.util.PasswordHasher;
import jakarta.persistence.EntityNotFoundException; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional 
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) { 
        if (userRepository.findByUsername(registrationDTO.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.findByEmail(registrationDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFullName(registrationDTO.getFullName());
        user.setPhone(registrationDTO.getPhone());
        user.setDateOfBirth(registrationDTO.getDateOfBirth());
        user.setGender(registrationDTO.getGender());
        user.setUserRole(registrationDTO.getUserRole());
        String hashedPassword = PasswordHasher.hashStringTo10Digits(registrationDTO.getPassword());
        user.setPasswordHash(hashedPassword);
        User savedUser = userRepository.save(user);
        return getUserById(savedUser.getUserId());
    }

    @Override
    @Transactional(readOnly = true) 
    public UserResponseDTO findByUsername(String username) { 
        User user = userRepository.findByUsernameWithDetails(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        return new UserResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true) 
    public UserResponseDTO findByEmail(String email) { 
        User user = userRepository.findByEmailWithDetails(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return new UserResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true) 
    public UserResponseDTO getUserById(Integer userId) { 
        User user = userRepository.findByIdWithDetails(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return new UserResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        String hashedRawPassword = PasswordHasher.hashStringTo10Digits(rawPassword);
        return hashedRawPassword.equals(user.getPasswordHash()); 
    }
}
