package com.veridian.common.domain;

public enum OrderStatus {
    PENDING_RISK_CHECK,
    ACCEPTED,
    REJECTED,
    ROUTED,
    PARTIALLY_FILLED,
    FILLED,
    CANCELLED,
    EXPIRED
}
