package com.veridian.riskengine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircuitBreakerServiceTest {

    private CircuitBreakerService service;

    @BeforeEach
    void setUp() {
        service = new CircuitBreakerService();
    }

    @Test
    @DisplayName("Should initially have normal trading state without any halts")
    void testInitialState() {
        assertFalse(service.isGlobalHalt());
        assertFalse(service.isTradingHalted("BTC-USD"));
        assertFalse(service.isTradingHalted("ETH-USD"));
    }

    @Test
    @DisplayName("Should halt and resume specific trading symbols independently")
    void testSymbolLevelHaltAndResume() {
        service.haltSymbol("BTC-USD", "Excessive volatility detected");

        assertTrue(service.isTradingHalted("BTC-USD"));
        assertFalse(service.isTradingHalted("ETH-USD"));
        assertFalse(service.isGlobalHalt());

        service.resumeSymbol("BTC-USD");
        assertFalse(service.isTradingHalted("BTC-USD"));
    }

    @Test
    @DisplayName("Should trip and reset global market-wide circuit breaker")
    void testGlobalCircuitBreaker() {
        service.tripGlobalCircuitBreaker("Market-wide liquidity drain");

        assertTrue(service.isGlobalHalt());
        assertTrue(service.isTradingHalted("BTC-USD"));
        assertTrue(service.isTradingHalted("ETH-USD"));
        assertTrue(service.isTradingHalted("AAPL"));

        service.resetGlobalCircuitBreaker();
        assertFalse(service.isGlobalHalt());
        assertFalse(service.isTradingHalted("BTC-USD"));
    }
}
