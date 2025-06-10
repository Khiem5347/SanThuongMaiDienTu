package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.User.Gender;
import java.sql.Date; 

public class UserProfileUpdateDTO {
    private String fullName;
    private Gender gender;
    private Date dateOfBirth;
    private String phone;
    public UserProfileUpdateDTO() {
    }
    public UserProfileUpdateDTO(String fullName, Gender gender, Date dateOfBirth, String phone) {
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
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
    public String getPhone() {
        return phone;
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
    public void setPhone(String phone) {
        this.phone = phone;
    }
}