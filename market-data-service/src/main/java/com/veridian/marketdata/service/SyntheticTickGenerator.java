package com.veridian.marketdata.service;

import com.veridian.marketdata.model.MarketDepth;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SyntheticTickGenerator {

    private final Map<String, BigDecimal> basePrices = new ConcurrentHashMap<>();
    private final AtomicLong seqCounter = new AtomicLong(1);

    public SyntheticTickGenerator() {
        basePrices.put("BTC-USD", new BigDecimal("65000.00"));
        basePrices.put("ETH-USD", new BigDecimal("3500.00"));
        basePrices.put("SOL-USD", new BigDecimal("145.00"));
        basePrices.put("AAPL", new BigDecimal("185.50"));
        basePrices.put("NVDA", new BigDecimal("890.00"));
    }

    public MarketDepth generateSyntheticL2(String symbol) {
        BigDecimal midPrice = basePrices.getOrDefault(symbol, new BigDecimal("100.00"));
        // Random walk +/- 0.15%
        double pctChange = (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.003;
        midPrice = midPrice.multiply(BigDecimal.valueOf(1 + pctChange)).setScale(2, RoundingMode.HALF_UP);
        basePrices.put(symbol, midPrice);

        List<MarketDepth.Level> bids = new ArrayList<>();
        List<MarketDepth.Level> asks = new ArrayList<>();

        BigDecimal tickSize = midPrice.compareTo(new BigDecimal("1000")) > 0 ? new BigDecimal("0.50") : new BigDecimal("0.05");

        for (int i = 1; i <= 10; i++) {
            BigDecimal bidPrice = midPrice.subtract(tickSize.multiply(BigDecimal.valueOf(i)));
            BigDecimal askPrice = midPrice.add(tickSize.multiply(BigDecimal.valueOf(i)));
            BigDecimal bidQty = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1.0, 20.0)).setScale(4, RoundingMode.HALF_UP);
            BigDecimal askQty = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1.0, 20.0)).setScale(4, RoundingMode.HALF_UP);
            int count = ThreadLocalRandom.current().nextInt(1, 15);

            bids.add(new MarketDepth.Level(bidPrice, bidQty, count));
            asks.add(new MarketDepth.Level(askPrice, askQty, count));
        }

        return new MarketDepth(symbol, seqCounter.getAndIncrement(), Instant.now().toEpochMilli(), bids, asks);
    }
}
