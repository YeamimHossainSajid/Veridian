package com.veridian.orderservice.repository;

import com.veridian.orderservice.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByAccountId(String accountId);
    List<OrderEntity> findBySymbol(String symbol);
    List<OrderEntity> findByAccountIdAndSymbol(String accountId, String symbol);
}
