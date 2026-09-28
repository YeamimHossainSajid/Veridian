package com.veridian.matchingengine.engine;

import com.veridian.common.domain.OrderSide;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBook {

    private final String symbol;
    // Bids sorted descending: highest price first
    private final NavigableMap<BigDecimal, PriceLevel> bids = new TreeMap<>(Collections.reverseOrder());
    // Asks sorted ascending: lowest price first
    private final NavigableMap<BigDecimal, PriceLevel> asks = new TreeMap<>();
    // Fast O(1) lookup for order cancellation
    private final Map<String, BookOrder> orderLookup = new ConcurrentHashMap<>();

    public OrderBook(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public NavigableMap<BigDecimal, PriceLevel> getBids() {
        return bids;
    }

    public NavigableMap<BigDecimal, PriceLevel> getAsks() {
        return asks;
    }

    public Map<String, BookOrder> getOrderLookup() {
        return orderLookup;
    }

    public void addOrderToBook(BookOrder order) {
        orderLookup.put(order.getOrderId(), order);
        NavigableMap<BigDecimal, PriceLevel> bookSide = order.getSide() == OrderSide.BUY ? bids : asks;
        bookSide.computeIfAbsent(order.getPrice(), PriceLevel::new).addOrder(order);
    }

    public boolean cancelOrder(String orderId) {
        BookOrder order = orderLookup.remove(orderId);
        if (order == null) {
            return false;
        }
        NavigableMap<BigDecimal, PriceLevel> bookSide = order.getSide() == OrderSide.BUY ? bids : asks;
        PriceLevel level = bookSide.get(order.getPrice());
        if (level != null) {
            boolean removed = level.removeOrder(orderId);
            if (level.isEmpty()) {
                bookSide.remove(order.getPrice());
            }
            return removed;
        }
        return false;
    }

    public BigDecimal getBestBid() {
        return bids.isEmpty() ? null : bids.firstKey();
    }

    public BigDecimal getBestAsk() {
        return asks.isEmpty() ? null : asks.firstKey();
    }
}
