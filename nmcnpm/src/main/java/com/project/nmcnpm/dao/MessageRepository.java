package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByConversationConversationIdOrderByMessageTimeAsc(Integer conversationId);
    List<Message> findBySenderUserId(Integer senderId);
}