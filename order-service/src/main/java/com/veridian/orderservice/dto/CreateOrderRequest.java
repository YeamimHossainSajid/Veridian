package com.veridian.orderservice.dto;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;
import com.veridian.common.domain.TimeInForce;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateOrderRequest {

    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Order side is required")
    private OrderSide side;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    @DecimalMin(value = "0.0001", message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0001", message = "Quantity must be positive")
    private BigDecimal quantity;

    private TimeInForce timeInForce = TimeInForce.GTC;

    public CreateOrderRequest() {}

    public CreateOrderRequest(String accountId, String symbol, OrderSide side,
                              OrderType orderType, BigDecimal price,
                              BigDecimal quantity, TimeInForce timeInForce) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.timeInForce = timeInForce != null ? timeInForce : TimeInForce.GTC;
    }

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
}
