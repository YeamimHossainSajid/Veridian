package com.veridian.riskengine.service;

import com.veridian.common.event.OrderPlacedEvent;
import com.veridian.common.event.RiskAlertEvent;
import com.veridian.riskengine.model.AccountRiskProfile;
import com.veridian.riskengine.model.RiskEvaluationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RiskEngineService {

    private final CircuitBreakerService circuitBreakerService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<String, AccountRiskProfile> accountProfiles = new ConcurrentHashMap<>();

    @Value("${veridian.risk.max-order-notional:1000000.00}")
    private BigDecimal maxOrderNotional;

    @Value("${veridian.risk.max-position-notional:5000000.00}")
    private BigDecimal maxPositionNotional;

    public RiskEngineService(CircuitBreakerService circuitBreakerService,
                             KafkaTemplate<String, Object> kafkaTemplate) {
        this.circuitBreakerService = circuitBreakerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public AccountRiskProfile getOrCreateProfile(String accountId) {
        return accountProfiles.computeIfAbsent(accountId, AccountRiskProfile::new);
    }

    public RiskEvaluationResult evaluatePreTradeRisk(OrderPlacedEvent order) {
        // 1. Check Circuit Breaker
        if (circuitBreakerService.isTradingHalted(order.getSymbol())) {
            emitAlert(order.getAccountId(), order.getSymbol(), "CIRCUIT_BREAKER_ACTIVE", "CRITICAL",
                    "Trading is currently halted for " + order.getSymbol(), BigDecimal.ZERO, BigDecimal.ZERO);
            return RiskEvaluationResult.reject("Market halted by circuit breaker for " + order.getSymbol(), "HALTED");
        }

        // 2. Validate Order Notional Limit
        BigDecimal orderNotional = (order.getPrice() != null ? order.getPrice() : BigDecimal.ONE)
                .multiply(order.getQuantity());

        if (orderNotional.compareTo(maxOrderNotional) > 0) {
            emitAlert(order.getAccountId(), order.getSymbol(), "MAX_ORDER_NOTIONAL_EXCEEDED", "HIGH",
                    "Order value " + orderNotional + " exceeds max allowed " + maxOrderNotional,
                    orderNotional, maxOrderNotional);
            return RiskEvaluationResult.reject("Order notional exceeds limit of " + maxOrderNotional, "MAX_NOTIONAL");
        }

        // 3. Margin & Exposure Checks
        AccountRiskProfile profile = getOrCreateProfile(order.getAccountId());
        BigDecimal currentPosition = profile.getPosition(order.getSymbol());
        BigDecimal newPositionNotional = currentPosition.add(order.getQuantity()).multiply(order.getPrice() != null ? order.getPrice() : BigDecimal.ONE);

        if (newPositionNotional.compareTo(maxPositionNotional) > 0) {
            emitAlert(order.getAccountId(), order.getSymbol(), "MAX_POSITION_EXCEEDED", "HIGH",
                    "Position exposure would exceed " + maxPositionNotional,
                    newPositionNotional, maxPositionNotional);
            return RiskEvaluationResult.reject("Max position limit reached", "MAX_POSITION");
        }

        return RiskEvaluationResult.accept();
    }

    private void emitAlert(String accountId, String symbol, String alertType, String severity,
                           String message, BigDecimal current, BigDecimal threshold) {
        RiskAlertEvent alert = new RiskAlertEvent(
                "ALT-" + UUID.randomUUID(),
                accountId,
                symbol,
                alertType,
                severity,
                message,
                current,
                threshold,
                Instant.now()
        );
        kafkaTemplate.send("risk-alerts", accountId, alert);
    }
}
