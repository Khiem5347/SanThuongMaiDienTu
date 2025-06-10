package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.dto.UserProfileUpdateDTO;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.util.PasswordHasher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User registerUser(UserRegistrationDTO registrationDTO) {
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
        return userRepository.save(user);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        String hashedRawPassword = PasswordHasher.hashStringTo10Digits(rawPassword);
        return hashedRawPassword.equals(user.getPasswordHash());
    }
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        String hashedOldPassword = PasswordHasher.hashStringTo10Digits(oldPassword);
        if (!hashedOldPassword.equals(user.getPasswordHash())) {
            return false;
        }
        String hashedNewPassword = PasswordHasher.hashStringTo10Digits(newPassword);
        if (hashedNewPassword.equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password.");
        }
        user.setPasswordHash(hashedNewPassword);
        userRepository.save(user);
        return true;
    }
    @Override
    public User updateUserProfile(Integer userId, UserProfileUpdateDTO updateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
        if (updateDTO.getFullName() != null && !updateDTO.getFullName().isEmpty()) {
            user.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getGender() != null) {
            user.setGender(updateDTO.getGender());
        }
        if (updateDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(updateDTO.getDateOfBirth());
        }
        if (updateDTO.getPhone() != null && !updateDTO.getPhone().isEmpty()) {
            user.setPhone(updateDTO.getPhone());
        }

        return userRepository.save(user);
    }
    @Override
    public boolean updateEmail(String username, String currentPassword, String newEmail) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
        throw new IllegalArgumentException("User not found.");
    }
    if (!authenticateUser(username, currentPassword)) {
        return false; 
    }
    if (userRepository.existsByEmail(newEmail)) {
        throw new IllegalArgumentException("New email already in use by another account.");
    }
    user.setEmail(newEmail);
    userRepository.save(user);
    return true;
}
    @Override
    public boolean updateUsername(String currentUsername, String currentPassword, String newUsername) {
    User user = userRepository.findByUsername(currentUsername);
    if (user == null) {
        throw new IllegalArgumentException("User not found.");
    }
    if (!authenticateUser(currentUsername, currentPassword)) {
        return false; 
    }
    if (userRepository.existsByUsername(newUsername)) {
        throw new IllegalArgumentException("New username already taken.");
    }
    user.setUsername(newUsername);
    userRepository.save(user);

    return true;
}
}