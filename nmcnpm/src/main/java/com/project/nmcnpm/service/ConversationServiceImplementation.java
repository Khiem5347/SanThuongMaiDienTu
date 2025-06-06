package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ConversationRepository;
import com.project.nmcnpm.dao.UserRepository;
import com.project.nmcnpm.dto.ConversationDTO;
import com.project.nmcnpm.dto.ConversationResponseDTO;
import com.project.nmcnpm.entity.Conversation;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImplementation implements ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationServiceImplementation(ConversationRepository conversationRepository,
                                             UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Conversation createConversation(ConversationDTO conversationDTO) {
        if (conversationDTO.getBuyerId().equals(conversationDTO.getSellerId())) {
            throw new IllegalArgumentException("Cannot create a conversation between the same buyer and seller.");
        }
        Optional<Conversation> existingConversation = conversationRepository
                .findByBuyerUserIdAndSellerUserId(conversationDTO.getBuyerId(), conversationDTO.getSellerId());
        if (existingConversation.isPresent()) {
            throw new IllegalArgumentException("A conversation already exists between these users.");
        }

        User buyer = userRepository.findById(conversationDTO.getBuyerId())
                .orElseThrow(() -> new EntityNotFoundException("Buyer user not found with id: " + conversationDTO.getBuyerId()));
        User seller = userRepository.findById(conversationDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller user not found with id: " + conversationDTO.getSellerId()));

        Conversation conversation = new Conversation();
        conversation.setBuyer(buyer);
        conversation.setSeller(seller);
        return conversationRepository.save(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationResponseDTO getConversationById(Integer conversationId) {
        Conversation conversation = conversationRepository.findByIdWithUsers(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversationId));
        return new ConversationResponseDTO(conversation);
    }

    @Override
    @Transactional
    public void deleteConversation(Integer conversationId) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new EntityNotFoundException("Conversation not found with id: " + conversationId);
        }
        conversationRepository.deleteById(conversationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConversationResponseDTO> getAllConversations(Pageable pageable) {
        Page<Conversation> conversationsPage = conversationRepository.findAllWithUsers(pageable);
        return conversationsPage.map(ConversationResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDTO> getConversationsByBuyerId(Integer buyerId) {
        if (!userRepository.existsById(buyerId)) {
            throw new EntityNotFoundException("Buyer user not found with id: " + buyerId);
        }
        List<Conversation> conversations = conversationRepository.findByBuyerUserIdWithUsers(buyerId);
        return conversations.stream()
                .map(ConversationResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDTO> getConversationsBySellerId(Integer sellerId) {
        if (!userRepository.existsById(sellerId)) {
            throw new EntityNotFoundException("Seller user not found with id: " + sellerId);
        }
        List<Conversation> conversations = conversationRepository.findBySellerUserIdWithUsers(sellerId);
        return conversations.stream()
                .map(ConversationResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationResponseDTO getConversationBetweenUsers(Integer buyerId, Integer sellerId) {
        if (!userRepository.existsById(buyerId)) {
            throw new EntityNotFoundException("Buyer user not found with id: " + buyerId);
        }
        if (!userRepository.existsById(sellerId)) {
            throw new EntityNotFoundException("Seller user not found with id: " + sellerId);
        }
        Optional<Conversation> conversation = conversationRepository.findByBuyerUserIdAndSellerUserIdWithUsers(buyerId, sellerId);
        if (conversation.isEmpty()) {
            conversation = conversationRepository.findByBuyerUserIdAndSellerUserIdWithUsers(sellerId, buyerId); // Check reverse
        }
        return conversation.map(ConversationResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("No conversation found between user " + buyerId + " and user " + sellerId));
    }
}
