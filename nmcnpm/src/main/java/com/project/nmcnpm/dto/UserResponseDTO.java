package com.project.nmcnpm.dto;

import com.project.nmcnpm.entity.User;
import com.project.nmcnpm.entity.ProductReview; 
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String userRole;
    private List<Integer> productReviewIds;
    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.dateOfBirth = (user.getDateOfBirth() != null) ? user.getDateOfBirth().toLocalDate() : null; 
        this.gender = (user.getGender() != null) ? user.getGender().name() : null;
        this.userRole = user.getUserRole();
        if (user.getProductReviews() != null && !user.getProductReviews().isEmpty()) {
            this.productReviewIds = user.getProductReviews().stream()
                                      .map(ProductReview::getReviewId) 
                                      .collect(Collectors.toList());
        } else {
            this.productReviewIds = new ArrayList<>();
        }
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public List<Integer> getProductReviewIds() { return productReviewIds; }
    public void setProductReviewIds(List<Integer> productReviewIds) { this.productReviewIds = productReviewIds; }
}
