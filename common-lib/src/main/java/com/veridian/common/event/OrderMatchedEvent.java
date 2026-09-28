package com.veridian.common.event;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderMatchedEvent {
    private String eventId;
    private String matchId;
    private String buyOrderId;
    private String sellOrderId;
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private Instant matchedAt;

    public OrderMatchedEvent() {}

    public OrderMatchedEvent(String eventId, String matchId, String buyOrderId, String sellOrderId,
                             String symbol, BigDecimal price, BigDecimal quantity, Instant matchedAt) {
        this.eventId = eventId;
        this.matchId = matchId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.matchedAt = matchedAt;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getBuyOrderId() { return buyOrderId; }
    public void setBuyOrderId(String buyOrderId) { this.buyOrderId = buyOrderId; }

    public String getSellOrderId() { return sellOrderId; }
    public void setSellOrderId(String sellOrderId) { this.sellOrderId = sellOrderId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public Instant getMatchedAt() { return matchedAt; }
    public void setMatchedAt(Instant matchedAt) { this.matchedAt = matchedAt; }
}
