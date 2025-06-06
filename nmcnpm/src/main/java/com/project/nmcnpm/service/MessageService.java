package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.MessageDTO;
import com.project.nmcnpm.dto.MessageResponseDTO;
import com.project.nmcnpm.entity.Message;
import java.util.List;
public interface MessageService {
    Message createMessage(MessageDTO messageDTO);
    MessageResponseDTO getMessageById(Integer messageId);
    void deleteMessage(Integer messageId);
    List<MessageResponseDTO> getMessagesByConversationId(Integer conversationId);
    List<MessageResponseDTO> getMessagesBySenderId(Integer senderId);
    void markMessageAsRead(Integer messageId);
}
