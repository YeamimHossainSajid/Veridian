package com.veridian.settlement.controller;

import com.veridian.settlement.model.SettlementBatch;
import com.veridian.settlement.saga.SettlementSagaCoordinator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/settlements")
public class SettlementController {

    private final SettlementSagaCoordinator coordinator;

    public SettlementController(SettlementSagaCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @PostMapping("/clear")
    public ResponseEntity<SettlementBatch> clearTrade(@RequestBody Map<String, Object> req) {
        String tradeId = (String) req.get("tradeId");
        String buyer = (String) req.get("buyer");
        String seller = (String) req.get("seller");
        BigDecimal gross = new BigDecimal(req.get("grossAmount").toString());
        BigDecimal fee = new BigDecimal(req.get("feeAmount").toString());

        return ResponseEntity.ok(coordinator.processTradeClearing(tradeId, buyer, seller, gross, fee));
    }

    @GetMapping
    public ResponseEntity<List<SettlementBatch>> listSettlements() {
        return ResponseEntity.ok(coordinator.getAllSettlements());
    }
}
