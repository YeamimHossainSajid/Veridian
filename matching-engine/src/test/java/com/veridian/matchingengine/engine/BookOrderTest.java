package com.veridian.matchingengine.engine;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookOrderTest {

    @Test
    @DisplayName("Should report not filled when remaining quantity is greater than zero")
    void testRemainingQuantityNotFilled() {
        BookOrder order = new BookOrder(
                "ORD-001",
                "ACC-1",
                "BTC-USD",
                OrderSide.BUY,
                OrderType.LIMIT,
                new BigDecimal("65000.00"),
                new BigDecimal("2.5000"),
                System.nanoTime()
        );

        assertFalse(order.isFilled());
        assertEquals(new BigDecimal("2.5000"), order.getRemainingQuantity());

        // Partially fill
        order.setRemainingQuantity(new BigDecimal("1.0000"));
        assertFalse(order.isFilled());
        assertEquals(new BigDecimal("1.0000"), order.getRemainingQuantity());
    }

    @Test
    @DisplayName("Should report filled when remaining quantity reaches exactly zero")
    void testRemainingQuantityFilledZero() {
        BookOrder order = new BookOrder(
                "ORD-002",
                "ACC-1",
                "ETH-USD",
                OrderSide.SELL,
                OrderType.LIMIT,
                new BigDecimal("3500.00"),
                new BigDecimal("5.0000"),
                System.nanoTime()
        );

        order.setRemainingQuantity(BigDecimal.ZERO);
        assertTrue(order.isFilled());
    }

    @Test
    @DisplayName("Should report filled when remaining quantity becomes negative")
    void testRemainingQuantityNegative() {
        BookOrder order = new BookOrder(
                "ORD-003",
                "ACC-2",
                "SOL-USD",
                OrderSide.BUY,
                OrderType.MARKET,
                null,
                new BigDecimal("10.0000"),
                System.nanoTime()
        );

        order.setRemainingQuantity(new BigDecimal("-0.0001"));
        assertTrue(order.isFilled());
    }
}
