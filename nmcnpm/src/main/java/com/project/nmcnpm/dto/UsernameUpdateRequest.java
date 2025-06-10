package com.project.nmcnpm.dto;

public class UsernameUpdateRequest {
    private String currentUsername;
    private String currentPassword; 
    private String newUsername;
    public UsernameUpdateRequest() {}
    public UsernameUpdateRequest(String currentUsername, String currentPassword, String newUsername) {
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.newUsername = newUsername;
    }
    public String getCurrentUsername() {
        return currentUsername;
    }
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewUsername() {
        return newUsername;
    }
    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}