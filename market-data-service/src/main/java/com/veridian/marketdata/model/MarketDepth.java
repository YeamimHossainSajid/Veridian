package com.veridian.marketdata.model;

import java.math.BigDecimal;
import java.util.List;

public class MarketDepth {

    public static class Level {
        private BigDecimal price;
        private BigDecimal quantity;
        private int ordersCount;

        public Level() {}

        public Level(BigDecimal price, BigDecimal quantity, int ordersCount) {
            this.price = price;
            this.quantity = quantity;
            this.ordersCount = ordersCount;
        }

        public BigDecimal getPrice() { return price; }
        public BigDecimal getQuantity() { return quantity; }
        public int getOrdersCount() { return ordersCount; }
    }

    private String symbol;
    private long sequenceNumber;
    private long timestampEpochMs;
    private List<Level> bids;
    private List<Level> asks;

    public MarketDepth() {}

    public MarketDepth(String symbol, long sequenceNumber, long timestampEpochMs, List<Level> bids, List<Level> asks) {
        this.symbol = symbol;
        this.sequenceNumber = sequenceNumber;
        this.timestampEpochMs = timestampEpochMs;
        this.bids = bids;
        this.asks = asks;
    }

    public String getSymbol() { return symbol; }
    public long getSequenceNumber() { return sequenceNumber; }
    public long getTimestampEpochMs() { return timestampEpochMs; }
    public List<Level> getBids() { return bids; }
    public List<Level> getAsks() { return asks; }
}
