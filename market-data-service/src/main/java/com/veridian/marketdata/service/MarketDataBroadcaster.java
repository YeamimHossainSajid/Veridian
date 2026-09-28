package com.veridian.marketdata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veridian.marketdata.model.MarketDepth;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketDataBroadcaster {

    private final SyntheticTickGenerator tickGenerator;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final List<String> symbols = List.of("BTC-USD", "ETH-USD", "SOL-USD", "AAPL", "NVDA");

    public MarketDataBroadcaster(SyntheticTickGenerator tickGenerator,
                                 KafkaTemplate<String, Object> kafkaTemplate,
                                 ObjectMapper objectMapper) {
        this.tickGenerator = tickGenerator;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRateString = "${veridian.market-data.broadcast-interval-ms:100}")
    public void broadcastMarketData() {
        for (String symbol : symbols) {
            MarketDepth depth = tickGenerator.generateSyntheticL2(symbol);
            try {
                String payload = objectMapper.writeValueAsString(depth);
                kafkaTemplate.send("market-data", symbol, payload);
            } catch (Exception ignored) {
            }
        }
    }
}
