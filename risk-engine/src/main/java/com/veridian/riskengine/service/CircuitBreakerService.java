package com.veridian.riskengine.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CircuitBreakerService {

    private final Map<String, Boolean> symbolHalts = new ConcurrentHashMap<>();
    private volatile boolean globalHalt = false;

    public boolean isTradingHalted(String symbol) {
        return globalHalt || symbolHalts.getOrDefault(symbol, false);
    }

    public void haltSymbol(String symbol, String reason) {
        symbolHalts.put(symbol, true);
    }

    public void resumeSymbol(String symbol) {
        symbolHalts.remove(symbol);
    }

    public void tripGlobalCircuitBreaker(String reason) {
        this.globalHalt = true;
    }

    public void resetGlobalCircuitBreaker() {
        this.globalHalt = false;
        this.symbolHalts.clear();
    }

    public boolean isGlobalHalt() {
        return globalHalt;
    }
}
