package com.veridian.riskengine.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRiskProfile {

    private final String accountId;
    private BigDecimal cashBalance = new BigDecimal("1000000.00");
    private BigDecimal marginUsed = BigDecimal.ZERO;
    private BigDecimal dailyRealizedPnl = BigDecimal.ZERO;
    private final Map<String, BigDecimal> positions = new ConcurrentHashMap<>();

    public AccountRiskProfile(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() { return accountId; }
    public BigDecimal getCashBalance() { return cashBalance; }
    public void setCashBalance(BigDecimal cashBalance) { this.cashBalance = cashBalance; }
    public BigDecimal getMarginUsed() { return marginUsed; }
    public void setMarginUsed(BigDecimal marginUsed) { this.marginUsed = marginUsed; }
    public BigDecimal getDailyRealizedPnl() { return dailyRealizedPnl; }
    public void setDailyRealizedPnl(BigDecimal dailyRealizedPnl) { this.dailyRealizedPnl = dailyRealizedPnl; }
    public Map<String, BigDecimal> getPositions() { return positions; }

    public BigDecimal getPosition(String symbol) {
        return positions.getOrDefault(symbol, BigDecimal.ZERO);
    }

    public void updatePosition(String symbol, BigDecimal delta) {
        positions.merge(symbol, delta, BigDecimal::add);
    }
}
