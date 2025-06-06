package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    @Query("SELECT DISTINCT pr FROM ProductReview pr " +
           "JOIN FETCH pr.product p " +
           "JOIN FETCH pr.user u " +
           "LEFT JOIN FETCH pr.reviewImages ri " + // Use LEFT JOIN FETCH for collections
           "WHERE pr.reviewId = :reviewId")
    Optional<ProductReview> findByIdWithDetails(@Param("reviewId") Integer reviewId);
    @Query(value = "SELECT DISTINCT pr FROM ProductReview pr " +
                   "JOIN FETCH pr.product p " +
                   "JOIN FETCH pr.user u " +
                   "LEFT JOIN FETCH pr.reviewImages ri",
           countQuery = "SELECT count(pr) FROM ProductReview pr")
    Page<ProductReview> findAllWithDetails(Pageable pageable);
    @Query("SELECT DISTINCT pr FROM ProductReview pr " +
           "JOIN FETCH pr.product p " +
           "JOIN FETCH pr.user u " +
           "LEFT JOIN FETCH pr.reviewImages ri " +
           "WHERE pr.product.productId = :productId")
    List<ProductReview> findByProductProductIdWithDetails(@Param("productId") Integer productId);
    @Query("SELECT DISTINCT pr FROM ProductReview pr " +
           "JOIN FETCH pr.product p " +
           "JOIN FETCH pr.user u " +
           "LEFT JOIN FETCH pr.reviewImages ri " +
           "WHERE pr.user.userId = :userId")
    List<ProductReview> findByUserUserIdWithDetails(@Param("userId") Integer userId);
    List<ProductReview> findByProductProductId(Integer productId);
    List<ProductReview> findByUserUserId(Integer userId);
}
