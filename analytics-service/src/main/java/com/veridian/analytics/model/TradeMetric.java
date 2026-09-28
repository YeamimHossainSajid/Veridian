package com.veridian.analytics.model;

import java.math.BigDecimal;
import java.time.Instant;

public class TradeMetric {

    private String symbol;
    private long totalTrades;
    private BigDecimal totalVolume;
    private BigDecimal vwap;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Instant windowStart;
    private Instant windowEnd;

    public TradeMetric() {}

    public TradeMetric(String symbol, long totalTrades, BigDecimal totalVolume,
                       BigDecimal vwap, BigDecimal highPrice, BigDecimal lowPrice,
                       Instant windowStart, Instant windowEnd) {
        this.symbol = symbol;
        this.totalTrades = totalTrades;
        this.totalVolume = totalVolume;
        this.vwap = vwap;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
    }

    public String getSymbol() { return symbol; }
    public long getTotalTrades() { return totalTrades; }
    public BigDecimal getTotalVolume() { return totalVolume; }
    public BigDecimal getVwap() { return vwap; }
    public BigDecimal getHighPrice() { return highPrice; }
    public BigDecimal getLowPrice() { return lowPrice; }
    public Instant getWindowStart() { return windowStart; }
    public Instant getWindowEnd() { return windowEnd; }
}
