package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.productReviews pr WHERE u.userId = :userId")
    Optional<User> findByIdWithDetails(@Param("userId") Integer userId);
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.productReviews pr WHERE u.username = :username")
    Optional<User> findByUsernameWithDetails(@Param("username") String username);
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.productReviews pr WHERE u.email = :email")
    Optional<User> findByEmailWithDetails(@Param("email") String email);
}
