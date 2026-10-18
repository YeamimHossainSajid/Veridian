package com.veridian.common.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderStatus;
import com.veridian.common.domain.OrderType;
import com.veridian.common.domain.TimeInForce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderPlacedEventTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @DisplayName("Should correctly serialize and deserialize OrderPlacedEvent preserving BigDecimal precision")
    void testSerializationRoundTrip() throws Exception {
        Instant now = Instant.parse("2026-10-18T10:15:30.00Z");
        OrderPlacedEvent event = new OrderPlacedEvent(
                "EVT-001",
                "ORD-999",
                "ACC-100",
                "BTC-USD",
                OrderSide.BUY,
                OrderType.LIMIT,
                new BigDecimal("67540.25"),
                new BigDecimal("1.5000"),
                TimeInForce.GTC,
                OrderStatus.PENDING_RISK_CHECK,
                now
        );

        String json = objectMapper.writeValueAsString(event);
        assertNotNull(json);
        assertTrue(json.contains("BTC-USD"));
        assertTrue(json.contains("67540.25"));

        OrderPlacedEvent deserialized = objectMapper.readValue(json, OrderPlacedEvent.class);
        assertEquals(event.getEventId(), deserialized.getEventId());
        assertEquals(event.getOrderId(), deserialized.getOrderId());
        assertEquals(event.getAccountId(), deserialized.getAccountId());
        assertEquals(event.getSymbol(), deserialized.getSymbol());
        assertEquals(event.getSide(), deserialized.getSide());
        assertEquals(event.getOrderType(), deserialized.getOrderType());
        assertEquals(0, event.getPrice().compareTo(deserialized.getPrice()));
        assertEquals(0, event.getQuantity().compareTo(deserialized.getQuantity()));
        assertEquals(event.getTimeInForce(), deserialized.getTimeInForce());
        assertEquals(event.getStatus(), deserialized.getStatus());
        assertEquals(event.getTimestamp(), deserialized.getTimestamp());
    }

    @Test
    @DisplayName("Should handle no-args constructor and setters")
    void testNoArgsConstructorAndSetters() {
        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setEventId("EVT-002");
        event.setOrderId("ORD-1000");
        event.setSide(OrderSide.SELL);

        assertEquals("EVT-002", event.getEventId());
        assertEquals("ORD-1000", event.getOrderId());
        assertEquals(OrderSide.SELL, event.getSide());
    }
}
