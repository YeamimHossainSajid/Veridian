package com.veridian.matchingengine.engine;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class PriceLevel {

    private final BigDecimal price;
    private final Deque<BookOrder> orders = new ArrayDeque<>();
    private BigDecimal totalQuantity = BigDecimal.ZERO;

    public PriceLevel(BigDecimal price) {
        this.price = price;
    }

    public void addOrder(BookOrder order) {
        orders.addLast(order);
        totalQuantity = totalQuantity.add(order.getRemainingQuantity());
    }

    public BookOrder peekFirst() {
        return orders.peekFirst();
    }

    public BookOrder pollFirst() {
        BookOrder order = orders.pollFirst();
        if (order != null) {
            totalQuantity = totalQuantity.subtract(order.getRemainingQuantity());
        }
        return order;
    }

    public boolean removeOrder(String orderId) {
        for (BookOrder o : orders) {
            if (o.getOrderId().equals(orderId)) {
                orders.remove(o);
                totalQuantity = totalQuantity.subtract(o.getRemainingQuantity());
                return true;
            }
        }
        return false;
    }

    public void reduceQuantity(BigDecimal executedQty) {
        totalQuantity = totalQuantity.subtract(executedQty);
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public int getOrderCount() {
        return orders.size();
    }

    public BigDecimal getPrice() { return price; }
    public BigDecimal getTotalQuantity() { return totalQuantity; }
}
