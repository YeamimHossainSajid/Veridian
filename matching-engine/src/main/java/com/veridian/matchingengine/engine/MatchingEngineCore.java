package com.veridian.matchingengine.engine;

import com.veridian.common.domain.OrderSide;
import com.veridian.common.domain.OrderType;
import com.veridian.common.event.TradeExecutedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatchingEngineCore {

    private final Map<String, OrderBook> books = new ConcurrentHashMap<>();

    public OrderBook getOrCreateBook(String symbol) {
        return books.computeIfAbsent(symbol, OrderBook::new);
    }

    public MatchResult matchOrder(BookOrder incoming) {
        OrderBook book = getOrCreateBook(incoming.getSymbol());
        boolean isBuy = incoming.getSide() == OrderSide.BUY;
        NavigableMap<BigDecimal, PriceLevel> oppositeSide = isBuy ? book.getAsks() : book.getBids();

        MatchResult result = new MatchResult(incoming.getOrderId(), false, incoming.getRemainingQuantity());
        Iterator<Map.Entry<BigDecimal, PriceLevel>> levelIterator = oppositeSide.entrySet().iterator();

        while (levelIterator.hasNext() && !incoming.isFilled()) {
            Map.Entry<BigDecimal, PriceLevel> entry = levelIterator.next();
            BigDecimal restingPrice = entry.getKey();
            PriceLevel level = entry.getValue();

            // Price match check
            if (incoming.getOrderType() == OrderType.LIMIT) {
                if (isBuy && incoming.getPrice().compareTo(restingPrice) < 0) {
                    break; // incoming bid is lower than lowest ask
                }
                if (!isBuy && incoming.getPrice().compareTo(restingPrice) > 0) {
                    break; // incoming ask is higher than highest bid
                }
            }

            // Match at resting price (price-time priority)
            while (!level.isEmpty() && !incoming.isFilled()) {
                BookOrder restingOrder = level.peekFirst();
                BigDecimal matchQty = incoming.getRemainingQuantity().min(restingOrder.getRemainingQuantity());

                incoming.setRemainingQuantity(incoming.getRemainingQuantity().subtract(matchQty));
                restingOrder.setRemainingQuantity(restingOrder.getRemainingQuantity().subtract(matchQty));
                level.reduceQuantity(matchQty);

                TradeExecutedEvent trade = new TradeExecutedEvent(
                        "TRD-" + UUID.randomUUID(),
                        incoming.getOrderId(),
                        incoming.getAccountId(),
                        restingOrder.getAccountId(),
                        incoming.getSymbol(),
                        restingPrice,
                        matchQty,
                        matchQty.multiply(restingPrice).multiply(new BigDecimal("0.001")), // 10 bps fee
                        Instant.now()
                );
                result.addTrade(trade);

                if (restingOrder.isFilled()) {
                    level.pollFirst();
                    book.getOrderLookup().remove(restingOrder.getOrderId());
                }
            }

            if (level.isEmpty()) {
                levelIterator.remove();
            }
        }

        if (!incoming.isFilled() && incoming.getOrderType() == OrderType.LIMIT) {
            book.addOrderToBook(incoming);
        }

        return new MatchResult(incoming.getOrderId(), incoming.isFilled(), incoming.getRemainingQuantity());
    }

    public boolean cancelOrder(String symbol, String orderId) {
        OrderBook book = books.get(symbol);
        return book != null && book.cancelOrder(orderId);
    }
}
