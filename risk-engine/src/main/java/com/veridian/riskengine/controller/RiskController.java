package com.veridian.riskengine.controller;

import com.veridian.common.event.OrderPlacedEvent;
import com.veridian.riskengine.model.AccountRiskProfile;
import com.veridian.riskengine.model.RiskEvaluationResult;
import com.veridian.riskengine.service.CircuitBreakerService;
import com.veridian.riskengine.service.RiskEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/risk")
public class RiskController {

    private final RiskEngineService riskEngineService;
    private final CircuitBreakerService circuitBreakerService;

    public RiskController(RiskEngineService riskEngineService,
                          CircuitBreakerService circuitBreakerService) {
        this.riskEngineService = riskEngineService;
        this.circuitBreakerService = circuitBreakerService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<RiskEvaluationResult> evaluateOrder(@RequestBody OrderPlacedEvent order) {
        RiskEvaluationResult result = riskEngineService.evaluatePreTradeRisk(order);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountRiskProfile> getAccountProfile(@PathVariable String accountId) {
        return ResponseEntity.ok(riskEngineService.getOrCreateProfile(accountId));
    }

    @PostMapping("/circuit-breaker/halt/{symbol}")
    public ResponseEntity<String> haltSymbol(@PathVariable String symbol) {
        circuitBreakerService.haltSymbol(symbol, "Manual administrative intervention");
        return ResponseEntity.ok("Symbol " + symbol + " halted");
    }

    @PostMapping("/circuit-breaker/resume/{symbol}")
    public ResponseEntity<String> resumeSymbol(@PathVariable String symbol) {
        circuitBreakerService.resumeSymbol(symbol);
        return ResponseEntity.ok("Symbol " + symbol + " resumed");
    }
}
