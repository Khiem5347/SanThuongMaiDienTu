package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    @Query("SELECT c FROM Conversation c JOIN FETCH c.buyer b JOIN FETCH c.seller s WHERE c.conversationId = :conversationId")
    Optional<Conversation> findByIdWithUsers(@Param("conversationId") Integer conversationId);
    @Query(value = "SELECT c FROM Conversation c JOIN FETCH c.buyer b JOIN FETCH c.seller s",
           countQuery = "SELECT count(c) FROM Conversation c")
    Page<Conversation> findAllWithUsers(Pageable pageable);
    @Query("SELECT c FROM Conversation c JOIN FETCH c.buyer b JOIN FETCH c.seller s WHERE c.buyer.userId = :buyerId")
    List<Conversation> findByBuyerUserIdWithUsers(@Param("buyerId") Integer buyerId);
    @Query("SELECT c FROM Conversation c JOIN FETCH c.buyer b JOIN FETCH c.seller s WHERE c.seller.userId = :sellerId")
    List<Conversation> findBySellerUserIdWithUsers(@Param("sellerId") Integer sellerId);
    @Query("SELECT c FROM Conversation c JOIN FETCH c.buyer b JOIN FETCH c.seller s WHERE c.buyer.userId = :buyerId AND c.seller.userId = :sellerId")
    Optional<Conversation> findByBuyerUserIdAndSellerUserIdWithUsers(@Param("buyerId") Integer buyerId, @Param("sellerId") Integer sellerId);
    Optional<Conversation> findByBuyerUserIdAndSellerUserId(Integer buyerId, Integer sellerId);
    List<Conversation> findByBuyerUserId(Integer buyerId);
    List<Conversation> findBySellerUserId(Integer sellerId);
}
