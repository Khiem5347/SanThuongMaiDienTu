package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.HistoryRepository;
import com.project.nmcnpm.dao.UserRepository; 
import com.project.nmcnpm.dao.OrderRepository; 
import com.project.nmcnpm.dto.HistoryDTO;
import com.project.nmcnpm.dto.HistoryResponseDTO;
import com.project.nmcnpm.entity.History;
import com.project.nmcnpm.entity.Order;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImplementation implements HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public HistoryServiceImplementation(HistoryRepository historyRepository,
                                        UserRepository userRepository,
                                        OrderRepository orderRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    @Transactional
    public History createHistory(HistoryDTO historyDTO) {
        User user = userRepository.findById(historyDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + historyDTO.getUserId()));
        Order order = orderRepository.findById(historyDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + historyDTO.getOrderId()));
        if (historyRepository.findByOrderOrderId(historyDTO.getOrderId()) != null) {
            throw new IllegalArgumentException("History record already exists for order with ID: " + historyDTO.getOrderId());
        }
        History history = new History();
        history.setUser(user);
        history.setOrder(order);
        order.setHistory(history);
        orderRepository.save(order);
        return historyRepository.save(history);
    }
    @Override
    @Transactional(readOnly = true)
    public HistoryResponseDTO getHistoryById(Integer historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException("History not found with id: " + historyId));
        return new HistoryResponseDTO(history);
    }
    @Override
    @Transactional
    public void deleteHistory(Integer historyId) {
        History historyToDelete = historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException("History not found with id: " + historyId));
        if (historyToDelete.getOrder() != null) {
            historyToDelete.getOrder().setHistory(null);
            orderRepository.save(historyToDelete.getOrder());
        }
        historyRepository.delete(historyToDelete);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<HistoryResponseDTO> getAllHistories(Pageable pageable) {
        Page<History> historiesPage = historyRepository.findAll(pageable);
        return historiesPage.map(HistoryResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<HistoryResponseDTO> getHistoriesByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        List<History> histories = historyRepository.findByUserUserId(userId);
        return histories.stream()
                .map(HistoryResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public HistoryResponseDTO getHistoryByOrderId(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }
        History history = historyRepository.findByOrderOrderId(orderId);
        if (history == null) {
            throw new EntityNotFoundException("History record not found for order with id: " + orderId);
        }
        return new HistoryResponseDTO(history);
    }
}
