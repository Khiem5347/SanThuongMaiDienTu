package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.ConversationDTO;
import com.project.nmcnpm.dto.ConversationResponseDTO;
import com.project.nmcnpm.entity.Conversation;
import com.project.nmcnpm.service.ConversationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping
    public ResponseEntity<Conversation> createConversation(@Valid @RequestBody ConversationDTO conversationDTO) {
        try {
            Conversation createdConversation = conversationService.createConversation(conversationDTO);
            return new ResponseEntity<>(createdConversation, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating conversation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating conversation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.err.println("Internal server error creating conversation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponseDTO> getConversationById(@PathVariable Integer id) {
        try {
            ConversationResponseDTO conversation = conversationService.getConversationById(id);
            return new ResponseEntity<>(conversation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Conversation not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting conversation by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Integer id) {
        try {
            conversationService.deleteConversation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Conversation not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting conversation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<ConversationResponseDTO>> getAllConversations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ConversationResponseDTO> conversations = conversationService.getAllConversations(pageable);
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<ConversationResponseDTO>> getConversationsByBuyerId(@PathVariable Integer buyerId) {
        try {
            List<ConversationResponseDTO> conversations = conversationService.getConversationsByBuyerId(buyerId);
            return new ResponseEntity<>(conversations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Buyer user not found when fetching conversations: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting conversations by buyer ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ConversationResponseDTO>> getConversationsBySellerId(@PathVariable Integer sellerId) {
        try {
            List<ConversationResponseDTO> conversations = conversationService.getConversationsBySellerId(sellerId);
            return new ResponseEntity<>(conversations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Seller user not found when fetching conversations: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting conversations by seller ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/between-users")
    public ResponseEntity<ConversationResponseDTO> getConversationBetweenUsers(
            @RequestParam Integer buyerId,
            @RequestParam Integer sellerId) {
        try {
            ConversationResponseDTO conversation = conversationService.getConversationBetweenUsers(buyerId, sellerId);
            return new ResponseEntity<>(conversation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Conversation not found between users: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting conversation between users: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
