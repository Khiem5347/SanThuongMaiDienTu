package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.util.PasswordHasher; 
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
        //băm mật khẩukhẩu
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
        return hashedRawPassword.equals(user.getPasswordHash()); //so sánh mật khẩu với sql
    }
}