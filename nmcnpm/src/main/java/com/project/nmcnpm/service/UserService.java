package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.dto.UserResponseDTO; 
import com.project.nmcnpm.entity.User; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import java.util.List; 
public interface UserService {
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO); 
    UserResponseDTO findByUsername(String username); 
    UserResponseDTO findByEmail(String email);
    UserResponseDTO getUserById(Integer userId); 
    boolean authenticateUser(String username, String rawPassword);
}
