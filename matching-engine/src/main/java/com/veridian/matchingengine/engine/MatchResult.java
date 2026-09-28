package com.veridian.matchingengine.engine;

import com.veridian.common.event.TradeExecutedEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MatchResult {

    private final String incomingOrderId;
    private final boolean fullyFilled;
    private final BigDecimal remainingQuantity;
    private final List<TradeExecutedEvent> trades = new ArrayList<>();

    public MatchResult(String incomingOrderId, boolean fullyFilled, BigDecimal remainingQuantity) {
        this.incomingOrderId = incomingOrderId;
        this.fullyFilled = fullyFilled;
        this.remainingQuantity = remainingQuantity;
    }

    public void addTrade(TradeExecutedEvent trade) {
        trades.add(trade);
    }

    public String getIncomingOrderId() { return incomingOrderId; }
    public boolean isFullyFilled() { return fullyFilled; }
    public BigDecimal getRemainingQuantity() { return remainingQuantity; }
    public List<TradeExecutedEvent> getTrades() { return trades; }
}
