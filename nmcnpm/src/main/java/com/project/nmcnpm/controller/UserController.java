package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.LoginRequest;
import com.project.nmcnpm.dto.LoginResponse;
import com.project.nmcnpm.dto.ShopResponseDTO;
import com.project.nmcnpm.dto.UserRegistrationDTO;
import com.project.nmcnpm.dto.UserResponseDTO; 
import com.project.nmcnpm.service.ShopService;
import com.project.nmcnpm.service.UserService;
import jakarta.persistence.EntityNotFoundException; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final ShopService shopService;
    private final UserService userService;
    public UserController(UserService userService, ShopService shopService) {
        this.userService = userService;
        this.shopService = shopService; // Initialize shopService here
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO registrationDTO) { // Changed return type
        try {
            UserResponseDTO registeredUser = userService.registerUser(registrationDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error during registration: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return new ResponseEntity<>(new LoginResponse("Login successful", true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse("Invalid username or password", false), HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) { // Changed return type
        try {
            UserResponseDTO user = userService.findByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting user by username: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{userId}/shop")
    public ResponseEntity<ShopResponseDTO> getShopByUserId(@PathVariable Integer userId) {
        try {
            ShopResponseDTO shop = shopService.getShopByUserId(userId);
            return ResponseEntity.ok(shop);
        } catch (EntityNotFoundException e) {
            System.err.println("Shop not found for user ID " + userId + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting shop by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
