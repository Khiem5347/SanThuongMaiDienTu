package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.HistoryDTO;
import com.project.nmcnpm.dto.HistoryResponseDTO;
import com.project.nmcnpm.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface HistoryService {
    History createHistory(HistoryDTO historyDTO);
    HistoryResponseDTO getHistoryById(Integer historyId);
    void deleteHistory(Integer historyId);
    Page<HistoryResponseDTO> getAllHistories(Pageable pageable);
    List<HistoryResponseDTO> getHistoriesByUserId(Integer userId);
    HistoryResponseDTO getHistoryByOrderId(Integer orderId);
}
