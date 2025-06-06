package com.project.nmcnpm.controller;

import com.project.nmcnpm.dto.HistoryDTO;
import com.project.nmcnpm.dto.HistoryResponseDTO;
import com.project.nmcnpm.entity.History;
import com.project.nmcnpm.service.HistoryService;
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
@RequestMapping("/api/histories")
public class HistoryController {

    private final HistoryService historyService;
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    @PostMapping
    public ResponseEntity<History> createHistory(@Valid @RequestBody HistoryDTO historyDTO) {
        try {
            History createdHistory = historyService.createHistory(historyDTO);
            return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Error creating history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error creating history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.err.println("Internal server error creating history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<HistoryResponseDTO> getHistoryById(@PathVariable Integer id) {
        try {
            HistoryResponseDTO history = historyService.getHistoryById(id);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("History not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting history by ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Integer id) {
        try {
            historyService.deleteHistory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            System.err.println("History not found for deletion: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error deleting history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<Page<HistoryResponseDTO>> getAllHistories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HistoryResponseDTO> histories = historyService.getAllHistories(pageable);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HistoryResponseDTO>> getHistoriesByUserId(@PathVariable Integer userId) {
        try {
            List<HistoryResponseDTO> histories = historyService.getHistoriesByUserId(userId);
            return new ResponseEntity<>(histories, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("User not found when fetching histories: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting histories by user ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<HistoryResponseDTO> getHistoryByOrderId(@PathVariable Integer orderId) {
        try {
            HistoryResponseDTO history = historyService.getHistoryByOrderId(orderId);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("History not found for order ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Internal server error getting history by order ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
