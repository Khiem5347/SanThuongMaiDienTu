package com.project.nmcnpm.dto;

public class EmailUpdateRequest {
    private String username; 
    private String currentPassword; 
    private String newEmail;
    public EmailUpdateRequest() {}
    public EmailUpdateRequest(String username, String currentPassword, String newEmail) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newEmail = newEmail;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewEmail() {
        return newEmail;
    }
    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}