package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.LoginRequest;
import com.project.nmcnpm.dto.LoginResponse;
import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.dto.ChangePasswordRequest;
import com.project.nmcnpm.dto.ShopResponseDTO; 
import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.service.UserService;
import com.project.nmcnpm.service.ShopService; 
import jakarta.persistence.EntityNotFoundException; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ShopService shopService; 
    public UserController(UserService userService, ShopService shopService) {
        this.userService = userService;
        this.shopService = shopService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User registeredUser = userService.registerUser(registrationDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return new ResponseEntity<>(new LoginResponse("Login successful", true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse("Invalid username or password", false), HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            boolean success = userService.changePassword(request.getUsername(), request.getOldPassword(), request.getNewPassword());
            if (success) {
                return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Old password incorrect.", HttpStatus.UNAUTHORIZED);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error changing password: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{userId}/shop") 
    public ResponseEntity<ShopResponseDTO> getShopByUserId(@PathVariable Integer userId) {
        try {
            ShopResponseDTO shop = shopService.getShopByUserId(userId);
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error getting shop by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}