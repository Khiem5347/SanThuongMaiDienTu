package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByUserUserId(Integer userId);
    History findByOrderOrderId(Integer orderId);
}