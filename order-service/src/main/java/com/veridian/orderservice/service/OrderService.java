package com.veridian.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veridian.common.domain.OrderStatus;
import com.veridian.common.event.OrderPlacedEvent;
import com.veridian.orderservice.dto.CreateOrderRequest;
import com.veridian.orderservice.dto.OrderResponse;
import com.veridian.orderservice.model.OrderEntity;
import com.veridian.orderservice.model.OutboxEvent;
import com.veridian.orderservice.repository.OrderRepository;
import com.veridian.orderservice.repository.OutboxRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepository,
                        OutboxRepository outboxRepository,
                        KafkaTemplate<String, Object> kafkaTemplate,
                        ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public OrderResponse placeOrder(CreateOrderRequest req) {
        String orderId = "ORD-" + UUID.randomUUID().toString();
        Instant now = Instant.now();

        OrderEntity order = new OrderEntity(
                orderId,
                req.getAccountId(),
                req.getSymbol(),
                req.getSide(),
                req.getOrderType(),
                req.getPrice(),
                req.getQuantity(),
                BigDecimal.ZERO,
                req.getTimeInForce(),
                OrderStatus.PENDING_RISK_CHECK,
                now,
                now
        );
        orderRepository.save(order);

        OrderPlacedEvent event = new OrderPlacedEvent(
                UUID.randomUUID().toString(),
                orderId,
                order.getAccountId(),
                order.getSymbol(),
                order.getSide(),
                order.getOrderType(),
                order.getPrice(),
                order.getQuantity(),
                order.getTimeInForce(),
                order.getStatus(),
                now
        );

        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxEvent outbox = new OutboxEvent("Order", orderId, "OrderPlacedEvent", payload);
            outboxRepository.save(outbox);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize order event", e);
        }

        return OrderResponse.fromEntity(order);
    }

    public OrderResponse getOrder(String orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        return OrderResponse.fromEntity(order);
    }

    public List<OrderResponse> getOrdersByAccount(String accountId) {
        return orderRepository.findByAccountId(accountId)
                .stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    @Scheduled(fixedDelay = 200)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> pending = outboxRepository.findByProcessedFalseOrderByCreatedAtAsc(PageRequest.of(0, 50));
        for (OutboxEvent outbox : pending) {
            try {
                kafkaTemplate.send("order-events", outbox.getAggregateId(), outbox.getPayload());
                outbox.setProcessed(true);
            } catch (Exception ignored) {
                // Will retry on next tick
            }
        }
    }
}
