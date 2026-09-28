package com.veridian.common.event;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderStatus;
import com.veridian.common.domain.OrderType;
import com.veridian.common.domain.TimeInForce;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderPlacedEvent {
    private String eventId;
    private String orderId;
    private String accountId;
    private String symbol;
    private OrderSide side;
    private OrderType orderType;
    private BigDecimal price;
    private BigDecimal quantity;
    private TimeInForce timeInForce;
    private OrderStatus status;
    private Instant timestamp;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(String eventId, String orderId, String accountId, String symbol,
                            OrderSide side, OrderType orderType, BigDecimal price,
                            BigDecimal quantity, TimeInForce timeInForce, OrderStatus status,
                            Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.timeInForce = timeInForce;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public OrderSide getSide() { return side; }
    public void setSide(OrderSide side) { this.side = side; }

    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public TimeInForce getTimeInForce() { return timeInForce; }
    public void setTimeInForce(TimeInForce timeInForce) { this.timeInForce = timeInForce; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
