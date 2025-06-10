package com.project.nmcnpm.dto;

public class LoginResponse {
    private String message;
    private boolean success;
    private String token;    
    private Long userId;     
    private String username; 
    public LoginResponse(String message, boolean success, String token, Long userId, String username) {
        this.message = message;
        this.success = success;
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.token = null;
        this.userId = null;
        this.username = null;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getToken() {
        return token;
    }
    public Long getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}