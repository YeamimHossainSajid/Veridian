package com.veridian.matchingengine.engine;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;

import java.math.BigDecimal;

public class BookOrder {
    private final String orderId;
    private final String accountId;
    private final String symbol;
    private final OrderSide side;
    private final OrderType orderType;
    private final BigDecimal price;
    private BigDecimal remainingQuantity;
    private final long timestampNs;

    public BookOrder(String orderId, String accountId, String symbol, OrderSide side,
                     OrderType orderType, BigDecimal price, BigDecimal quantity, long timestampNs) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.orderType = orderType;
        this.price = price;
        this.remainingQuantity = quantity;
        this.timestampNs = timestampNs;
    }

    public String getOrderId() { return orderId; }
    public String getAccountId() { return accountId; }
    public String getSymbol() { return symbol; }
    public OrderSide getSide() { return side; }
    public OrderType getOrderType() { return orderType; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getRemainingQuantity() { return remainingQuantity; }
    public void setRemainingQuantity(BigDecimal remainingQuantity) { this.remainingQuantity = remainingQuantity; }
    public long getTimestampNs() { return timestampNs; }

    public boolean isFilled() {
        return remainingQuantity.compareTo(BigDecimal.ZERO) <= 0;
    }
}
