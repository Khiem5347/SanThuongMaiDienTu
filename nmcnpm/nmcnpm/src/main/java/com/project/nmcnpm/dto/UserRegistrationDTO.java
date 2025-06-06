package com.project.nmcnpm.dto;


import com.project.nmcnpm.entity.User.Gender;
import java.sql.Date;

public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password; 
    private String fullName;
    private Gender gender; 
    private Date dateOfBirth;
    private String avatarUrl;
    private String phone;
    private String userRole;
    public UserRegistrationDTO() {
    }
    public UserRegistrationDTO(String username, String email, String password, String fullName, Gender gender,
                               Date dateOfBirth, String avatarUrl, String phone, String userRole) { // userRole is String
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.userRole = userRole;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
    public Gender getGender() {
        return gender;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getPhone() {
        return phone;
    }
    public String getUserRole() { 
        return userRole;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setUserRole(String userRole) { 
        this.userRole = userRole;
    }
}
