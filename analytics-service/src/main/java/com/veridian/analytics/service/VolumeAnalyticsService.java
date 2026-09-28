package com.veridian.analytics.service;

import com.veridian.analytics.model.TradeMetric;
import com.veridian.common.event.TradeExecutedEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VolumeAnalyticsService {

    private static class RollingWindowStats {
        final AtomicLong tradeCount = new AtomicLong(0);
        BigDecimal volume = BigDecimal.ZERO;
        BigDecimal turnover = BigDecimal.ZERO;
        BigDecimal high = BigDecimal.ZERO;
        BigDecimal low = BigDecimal.valueOf(Double.MAX_VALUE);
    }

    private final Map<String, RollingWindowStats> statsMap = new ConcurrentHashMap<>();

    public synchronized void recordTrade(TradeExecutedEvent trade) {
        RollingWindowStats stats = statsMap.computeIfAbsent(trade.getSymbol(), s -> new RollingWindowStats());
        stats.tradeCount.incrementAndGet();
        stats.volume = stats.volume.add(trade.getExecutedQuantity());
        BigDecimal tradeNotional = trade.getExecutedPrice().multiply(trade.getExecutedQuantity());
        stats.turnover = stats.turnover.add(tradeNotional);

        if (trade.getExecutedPrice().compareTo(stats.high) > 0) {
            stats.high = trade.getExecutedPrice();
        }
        if (trade.getExecutedPrice().compareTo(stats.low) < 0) {
            stats.low = trade.getExecutedPrice();
        }
    }

    public TradeMetric getMetricsForSymbol(String symbol) {
        RollingWindowStats stats = statsMap.get(symbol);
        Instant now = Instant.now();
        if (stats == null || stats.volume.compareTo(BigDecimal.ZERO) == 0) {
            return new TradeMetric(symbol, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                    now.minus(1, ChronoUnit.HOURS), now);
        }

        BigDecimal vwap = stats.turnover.divide(stats.volume, 4, RoundingMode.HALF_UP);
        return new TradeMetric(
                symbol,
                stats.tradeCount.get(),
                stats.volume,
                vwap,
                stats.high,
                stats.low,
                now.minus(1, ChronoUnit.HOURS),
                now
        );
    }
}
