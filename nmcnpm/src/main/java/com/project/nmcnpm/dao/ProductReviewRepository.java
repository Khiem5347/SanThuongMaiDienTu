package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findByProductProductId(Integer productId);
    List<ProductReview> findByUserUserId(Integer userId);
}