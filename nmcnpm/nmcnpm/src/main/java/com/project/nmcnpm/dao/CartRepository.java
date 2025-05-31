package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserUserId(Integer userId);
}