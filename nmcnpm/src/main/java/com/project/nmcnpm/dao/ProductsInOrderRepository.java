package com.project.nmcnpm.dao;

import com.project.nmcnpm.entity.ProductsInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductsInOrderRepository extends JpaRepository<ProductsInOrder, Integer> {
    List<ProductsInOrder> findByOrderOrderId(Integer orderId);
    List<ProductsInOrder> findByShopShopId(Integer shopId);
}