package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.ConversationDTO;
import com.project.nmcnpm.dto.ConversationResponseDTO;
import com.project.nmcnpm.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface ConversationService {
    Conversation createConversation(ConversationDTO conversationDTO);
    ConversationResponseDTO getConversationById(Integer conversationId);
    void deleteConversation(Integer conversationId);
    Page<ConversationResponseDTO> getAllConversations(Pageable pageable);
    List<ConversationResponseDTO> getConversationsByBuyerId(Integer buyerId);
    List<ConversationResponseDTO> getConversationsBySellerId(Integer sellerId);
    ConversationResponseDTO getConversationBetweenUsers(Integer buyerId, Integer sellerId);
}
