package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.entity.User;

public interface UserService {
    User registerUser(UserRegistrationDTO registrationDTO);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean authenticateUser(String username, String rawPassword);
    boolean changePassword(String username, String oldPassword, String newPassword);
}