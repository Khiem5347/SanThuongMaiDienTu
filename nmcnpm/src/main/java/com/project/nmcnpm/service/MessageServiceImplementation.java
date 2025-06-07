package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.MessageRepository;
import com.project.nmcnpm.dao.ConversationRepository; 
import com.project.nmcnpm.dao.UserRepository;     
import com.project.nmcnpm.dto.MessageDTO;
import com.project.nmcnpm.dto.MessageResponseDTO;
import com.project.nmcnpm.entity.Conversation;
import com.project.nmcnpm.entity.Message;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImplementation implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    public MessageServiceImplementation(MessageRepository messageRepository,
                                        ConversationRepository conversationRepository,
                                        UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public Message createMessage(MessageDTO messageDTO) {
        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + messageDTO.getConversationId()));
        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender user not found with id: " + messageDTO.getSenderId()));
        if (!conversation.getBuyer().getUserId().equals(sender.getUserId()) &&
            !conversation.getSeller().getUserId().equals(sender.getUserId())) {
            throw new IllegalArgumentException("Sender is not part of this conversation.");
        }
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setMessText(messageDTO.getMessText());
        message.setIsRead(false);
        conversation.addMessage(message);
        return messageRepository.save(message);
    }
    @Override
    @Transactional(readOnly = true)
    public MessageResponseDTO getMessageById(Integer messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + messageId));
        return new MessageResponseDTO(message);
    }
    @Override
    @Transactional
    public void deleteMessage(Integer messageId) {
        Message messageToDelete = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + messageId));
        if (messageToDelete.getConversation() != null) {
            messageToDelete.getConversation().removeMessage(messageToDelete);
        }
        messageRepository.delete(messageToDelete);
    }
    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDTO> getMessagesByConversationId(Integer conversationId) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new EntityNotFoundException("Conversation not found with id: " + conversationId);
        }
        List<Message> messages = messageRepository.findByConversationConversationIdOrderByMessageTimeAsc(conversationId);
        return messages.stream()
                .map(MessageResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDTO> getMessagesBySenderId(Integer senderId) {
        if (!userRepository.existsById(senderId)) {
            throw new EntityNotFoundException("User (sender) not found with id: " + senderId);
        }
        List<Message> messages = messageRepository.findBySenderUserId(senderId);
        return messages.stream()
                .map(MessageResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void markMessageAsRead(Integer messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + messageId));
        message.setIsRead(true);
        messageRepository.save(message);
    }
}
