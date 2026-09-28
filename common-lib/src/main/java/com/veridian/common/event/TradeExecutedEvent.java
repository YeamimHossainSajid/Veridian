package com.veridian.common.event;

import java.math.BigDecimal;
import java.time.Instant;

public class TradeExecutedEvent {
    private String tradeId;
    private String orderId;
    private String accountId;
    private String counterpartyAccountId;
    private String symbol;
    private BigDecimal executedPrice;
    private BigDecimal executedQuantity;
    private BigDecimal fee;
    private Instant executedAt;

    public TradeExecutedEvent() {}

    public TradeExecutedEvent(String tradeId, String orderId, String accountId,
                              String counterpartyAccountId, String symbol,
                              BigDecimal executedPrice, BigDecimal executedQuantity,
                              BigDecimal fee, Instant executedAt) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.accountId = accountId;
        this.counterpartyAccountId = counterpartyAccountId;
        this.symbol = symbol;
        this.executedPrice = executedPrice;
        this.executedQuantity = executedQuantity;
        this.fee = fee;
        this.executedAt = executedAt;
    }

    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCounterpartyAccountId() { return counterpartyAccountId; }
    public void setCounterpartyAccountId(String counterpartyAccountId) { this.counterpartyAccountId = counterpartyAccountId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getExecutedPrice() { return executedPrice; }
    public void setExecutedPrice(BigDecimal executedPrice) { this.executedPrice = executedPrice; }

    public BigDecimal getExecutedQuantity() { return executedQuantity; }
    public void setExecutedQuantity(BigDecimal executedQuantity) { this.executedQuantity = executedQuantity; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public Instant getExecutedAt() { return executedAt; }
    public void setExecutedAt(Instant executedAt) { this.executedAt = executedAt; }
}
