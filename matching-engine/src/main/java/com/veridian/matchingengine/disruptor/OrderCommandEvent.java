package com.veridian.matchingengine.disruptor;

import com.lmax.disruptor.EventFactory;
import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;

import java.math.BigDecimal;

public class OrderCommandEvent {

    public enum CommandType {
        PLACE_ORDER,
        CANCEL_ORDER
    }

    private CommandType commandType;
    private String orderId;
    private String accountId;
    private String symbol;
    private OrderSide side;
    private OrderType orderType;
    private BigDecimal price;
    private BigDecimal quantity;
    private long timestampNs;

    public void clear() {
        this.commandType = null;
        this.orderId = null;
        this.accountId = null;
        this.symbol = null;
        this.side = null;
        this.orderType = null;
        this.price = null;
        this.quantity = null;
        this.timestampNs = 0L;
    }

    public static final EventFactory<OrderCommandEvent> FACTORY = OrderCommandEvent::new;

    public CommandType getCommandType() { return commandType; }
    public void setCommandType(CommandType commandType) { this.commandType = commandType; }

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

    public long getTimestampNs() { return timestampNs; }
    public void setTimestampNs(long timestampNs) { this.timestampNs = timestampNs; }
}
