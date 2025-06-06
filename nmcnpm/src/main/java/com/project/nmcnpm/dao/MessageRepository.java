package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m JOIN FETCH m.conversation c JOIN FETCH m.sender s WHERE m.messageId = :messageId")
    Optional<Message> findByIdWithConversationAndSender(@Param("messageId") Integer messageId);
    @Query("SELECT m FROM Message m JOIN FETCH m.conversation c JOIN FETCH m.sender s WHERE m.conversation.conversationId = :conversationId ORDER BY m.messageTime ASC")
    List<Message> findByConversationConversationIdOrderByMessageTimeAscWithConversationAndSender(@Param("conversationId") Integer conversationId);
    @Query("SELECT m FROM Message m JOIN FETCH m.conversation c JOIN FETCH m.sender s WHERE m.sender.userId = :senderId")
    List<Message> findBySenderUserIdWithConversationAndSender(@Param("senderId") Integer senderId);
    List<Message> findByConversationConversationIdOrderByMessageTimeAsc(Integer conversationId);
    List<Message> findBySenderUserId(Integer senderId);
}
