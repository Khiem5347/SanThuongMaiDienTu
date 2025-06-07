package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.MessageDTO;
import com.project.nmcnpm.dto.MessageResponseDTO;
import com.project.nmcnpm.entity.Message;
import com.project.nmcnpm.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        try {
            Message createdMessage = messageService.createMessage(messageDTO);
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Internal server error creating message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> getMessageById(@PathVariable Integer id) {
        try {
            MessageResponseDTO message = messageService.getMessageById(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Message not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting message by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Message not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesByConversationId(@PathVariable Integer conversationId) {
        try {
            List<MessageResponseDTO> messages = messageService.getMessagesByConversationId(conversationId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Conversation not found when fetching messages: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting messages by conversation ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesBySenderId(@PathVariable Integer senderId) {
        try {
            List<MessageResponseDTO> messages = messageService.getMessagesBySenderId(senderId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User (sender) not found when fetching messages: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting messages by sender ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markMessageAsRead(@PathVariable Integer id) {
        try {
            messageService.markMessageAsRead(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("Message not found for marking as read: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error marking message as read: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
