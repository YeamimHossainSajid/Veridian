package com.veridian.matchingengine.engine;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceLevelTest {

    @Test
    @DisplayName("Should maintain FIFO queue order for orders at the same price level")
    void testFifoQueueOrder() {
        BigDecimal price = new BigDecimal("65000.00");
        PriceLevel level = new PriceLevel(price);

        BookOrder order1 = new BookOrder("ORD-1", "ACC-1", "BTC-USD", OrderSide.BUY, OrderType.LIMIT, price, new BigDecimal("1.0"), 1000L);
        BookOrder order2 = new BookOrder("ORD-2", "ACC-2", "BTC-USD", OrderSide.BUY, OrderType.LIMIT, price, new BigDecimal("2.5"), 2000L);
        BookOrder order3 = new BookOrder("ORD-3", "ACC-3", "BTC-USD", OrderSide.BUY, OrderType.LIMIT, price, new BigDecimal("0.5"), 3000L);

        level.addOrder(order1);
        level.addOrder(order2);
        level.addOrder(order3);

        assertEquals(3, level.getOrderCount());
        assertEquals(new BigDecimal("4.0"), level.getTotalQuantity());
        assertEquals("ORD-1", level.peekFirst().getOrderId());

        // First out
        BookOrder polled1 = level.pollFirst();
        assertEquals("ORD-1", polled1.getOrderId());
        assertEquals(2, level.getOrderCount());
        assertEquals(new BigDecimal("3.0"), level.getTotalQuantity());

        // Second out
        BookOrder polled2 = level.pollFirst();
        assertEquals("ORD-2", polled2.getOrderId());

        // Third out
        BookOrder polled3 = level.pollFirst();
        assertEquals("ORD-3", polled3.getOrderId());
        assertTrue(level.isEmpty());
        assertEquals(BigDecimal.ZERO, level.getTotalQuantity());
    }

    @Test
    @DisplayName("Should allow specific order cancellation by orderId")
    void testRemoveOrderById() {
        BigDecimal price = new BigDecimal("180.00");
        PriceLevel level = new PriceLevel(price);

        BookOrder o1 = new BookOrder("ORD-A", "ACC-1", "AAPL", OrderSide.SELL, OrderType.LIMIT, price, new BigDecimal("10.0"), 100L);
        BookOrder o2 = new BookOrder("ORD-B", "ACC-2", "AAPL", OrderSide.SELL, OrderType.LIMIT, price, new BigDecimal("15.0"), 200L);

        level.addOrder(o1);
        level.addOrder(o2);
        assertEquals(new BigDecimal("25.0"), level.getTotalQuantity());

        // Remove non-existent
        assertFalse(level.removeOrder("ORD-NON-EXISTENT"));

        // Remove order B
        assertTrue(level.removeOrder("ORD-B"));
        assertEquals(1, level.getOrderCount());
        assertEquals(new BigDecimal("10.0"), level.getTotalQuantity());
        assertEquals("ORD-A", level.peekFirst().getOrderId());
    }

    @Test
    @DisplayName("Should reduce quantity accurately on partial match")
    void testReduceQuantity() {
        BigDecimal price = new BigDecimal("3500.00");
        PriceLevel level = new PriceLevel(price);

        BookOrder o = new BookOrder("ORD-ETH", "ACC-1", "ETH-USD", OrderSide.BUY, OrderType.LIMIT, price, new BigDecimal("10.0"), 100L);
        level.addOrder(o);

        level.reduceQuantity(new BigDecimal("3.5"));
        assertEquals(new BigDecimal("6.5"), level.getTotalQuantity());
    }
}
