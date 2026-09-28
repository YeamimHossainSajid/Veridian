package com.veridian.common.event;

import java.math.BigDecimal;
import java.time.Instant;

public class RiskAlertEvent {
    private String alertId;
    private String accountId;
    private String symbol;
    private String alertType;
    private String severity;
    private String message;
    private BigDecimal currentExposure;
    private BigDecimal limitThreshold;
    private Instant timestamp;

    public RiskAlertEvent() {}

    public RiskAlertEvent(String alertId, String accountId, String symbol, String alertType,
                          String severity, String message, BigDecimal currentExposure,
                          BigDecimal limitThreshold, Instant timestamp) {
        this.alertId = alertId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.alertType = alertType;
        this.severity = severity;
        this.message = message;
        this.currentExposure = currentExposure;
        this.limitThreshold = limitThreshold;
        this.timestamp = timestamp;
    }

    public String getAlertId() { return alertId; }
    public void setAlertId(String alertId) { this.alertId = alertId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public BigDecimal getCurrentExposure() { return currentExposure; }
    public void setCurrentExposure(BigDecimal currentExposure) { this.currentExposure = currentExposure; }

    public BigDecimal getLimitThreshold() { return limitThreshold; }
    public void setLimitThreshold(BigDecimal limitThreshold) { this.limitThreshold = limitThreshold; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
