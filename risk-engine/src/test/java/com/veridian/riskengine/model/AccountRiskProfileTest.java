package com.veridian.riskengine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountRiskProfileTest {

    @Test
    @DisplayName("Should initialize account with default cash and zero margin")
    void testInitialState() {
        AccountRiskProfile profile = new AccountRiskProfile("ACC-TEST-1");

        assertEquals("ACC-TEST-1", profile.getAccountId());
        assertEquals(new BigDecimal("1000000.00"), profile.getCashBalance());
        assertEquals(BigDecimal.ZERO, profile.getMarginUsed());
        assertEquals(BigDecimal.ZERO, profile.getDailyRealizedPnl());
        assertEquals(BigDecimal.ZERO, profile.getPosition("BTC-USD"));
    }

    @Test
    @DisplayName("Should correctly update positions with positive and negative deltas")
    void testPositionUpdates() {
        AccountRiskProfile profile = new AccountRiskProfile("ACC-TEST-2");

        // Buy 5.5 BTC
        profile.updatePosition("BTC-USD", new BigDecimal("5.5"));
        assertEquals(new BigDecimal("5.5"), profile.getPosition("BTC-USD"));

        // Sell 2.0 BTC
        profile.updatePosition("BTC-USD", new BigDecimal("-2.0"));
        assertEquals(new BigDecimal("3.5"), profile.getPosition("BTC-USD"));

        // Independent symbol update
        profile.updatePosition("ETH-USD", new BigDecimal("20.0"));
        assertEquals(new BigDecimal("20.0"), profile.getPosition("ETH-USD"));
        assertEquals(new BigDecimal("3.5"), profile.getPosition("BTC-USD"));
    }

    @Test
    @DisplayName("Should update margin and realized PnL")
    void testMarginAndPnlUpdates() {
        AccountRiskProfile profile = new AccountRiskProfile("ACC-TEST-3");

        profile.setMarginUsed(new BigDecimal("50000.00"));
        profile.setDailyRealizedPnl(new BigDecimal("1250.75"));

        assertEquals(new BigDecimal("50000.00"), profile.getMarginUsed());
        assertEquals(new BigDecimal("1250.75"), profile.getDailyRealizedPnl());
    }
}
