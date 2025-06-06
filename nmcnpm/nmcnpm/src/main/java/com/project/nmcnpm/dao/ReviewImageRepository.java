package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
    List<ReviewImage> findByProductReviewReviewId(Integer reviewId);
}