package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findByBuyerUserId(Integer buyerId);
    List<Conversation> findBySellerUserId(Integer sellerId);
    Optional<Conversation> findByBuyerUserIdAndSellerUserId(Integer buyerId, Integer sellerId);
}