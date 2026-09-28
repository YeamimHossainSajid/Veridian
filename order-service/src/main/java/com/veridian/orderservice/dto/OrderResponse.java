package com.veridian.orderservice.dto;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderStatus;
import com.veridian.common.domain.OrderType;
import com.veridian.common.domain.TimeInForce;
import com.veridian.orderservice.model.OrderEntity;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderResponse {
    private String orderId;
    private String accountId;
    private String symbol;
    private OrderSide side;
    private OrderType orderType;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal executedQuantity;
    private TimeInForce timeInForce;
    private OrderStatus status;
    private Instant createdAt;

    public OrderResponse() {}

    public static OrderResponse fromEntity(OrderEntity entity) {
        OrderResponse resp = new OrderResponse();
        resp.orderId = entity.getOrderId();
        resp.accountId = entity.getAccountId();
        resp.symbol = entity.getSymbol();
        resp.side = entity.getSide();
        resp.orderType = entity.getOrderType();
        resp.price = entity.getPrice();
        resp.quantity = entity.getQuantity();
        resp.executedQuantity = entity.getExecutedQuantity();
        resp.timeInForce = entity.getTimeInForce();
        resp.status = entity.getStatus();
        resp.createdAt = entity.getCreatedAt();
        return resp;
    }

    public String getOrderId() { return orderId; }
    public String getAccountId() { return accountId; }
    public String getSymbol() { return symbol; }
    public OrderSide getSide() { return side; }
    public OrderType getOrderType() { return orderType; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getExecutedQuantity() { return executedQuantity; }
    public TimeInForce getTimeInForce() { return timeInForce; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
