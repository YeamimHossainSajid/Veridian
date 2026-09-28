package com.veridian.payment.controller;

import com.veridian.payment.model.LedgerEntry;
import com.veridian.payment.service.PaymentLedgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentLedgerService paymentLedgerService;

    public PaymentController(PaymentLedgerService paymentLedgerService) {
        this.paymentLedgerService = paymentLedgerService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<LedgerEntry> deposit(@RequestBody Map<String, Object> req) {
        String accountId = (String) req.get("accountId");
        String currency = (String) req.get("currency");
        BigDecimal amount = new BigDecimal(req.get("amount").toString());
        return ResponseEntity.ok(paymentLedgerService.recordDeposit(accountId, currency, amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<LedgerEntry> withdraw(@RequestBody Map<String, Object> req) {
        String accountId = (String) req.get("accountId");
        String currency = (String) req.get("currency");
        BigDecimal amount = new BigDecimal(req.get("amount").toString());
        return ResponseEntity.ok(paymentLedgerService.recordWithdrawal(accountId, currency, amount));
    }

    @GetMapping("/ledger/{accountId}")
    public ResponseEntity<List<LedgerEntry>> getLedger(@PathVariable String accountId) {
        return ResponseEntity.ok(paymentLedgerService.getAccountEntries(accountId));
    }
}
