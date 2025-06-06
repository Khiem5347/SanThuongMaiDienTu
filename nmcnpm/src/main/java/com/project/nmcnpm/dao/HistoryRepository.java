package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query("SELECT h FROM History h JOIN FETCH h.user u JOIN FETCH h.order o WHERE h.historyId = :historyId")
    Optional<History> findByIdWithUserAndOrder(@Param("historyId") Integer historyId);
    @Query(value = "SELECT h FROM History h JOIN FETCH h.user u JOIN FETCH h.order o",
           countQuery = "SELECT count(h) FROM History h")
    Page<History> findAllWithUserAndOrder(Pageable pageable);
    @Query("SELECT h FROM History h JOIN FETCH h.user u JOIN FETCH h.order o WHERE h.user.userId = :userId")
    List<History> findByUserUserIdWithUserAndOrder(@Param("userId") Integer userId);
    @Query("SELECT h FROM History h JOIN FETCH h.user u JOIN FETCH h.order o WHERE h.order.orderId = :orderId")
    History findByOrderOrderIdWithUserAndOrder(@Param("orderId") Integer orderId);
    History findByOrderOrderId(Integer orderId);
    List<History> findByUserUserId(Integer userId);
}
