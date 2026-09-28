package com.veridian.custody.controller;

import com.veridian.custody.model.AssetWallet;
import com.veridian.custody.service.CustodyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/custody")
public class CustodyController {

    private final CustodyService custodyService;

    public CustodyController(CustodyService custodyService) {
        this.custodyService = custodyService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<AssetWallet> reserve(@RequestBody Map<String, Object> req) {
        String accountId = (String) req.get("accountId");
        String asset = (String) req.get("asset");
        BigDecimal amount = new BigDecimal(req.get("amount").toString());
        return ResponseEntity.ok(custodyService.reserveBalance(accountId, asset, amount));
    }

    @PostMapping("/release")
    public ResponseEntity<AssetWallet> release(@RequestBody Map<String, Object> req) {
        String accountId = (String) req.get("accountId");
        String asset = (String) req.get("asset");
        BigDecimal amount = new BigDecimal(req.get("amount").toString());
        return ResponseEntity.ok(custodyService.releaseReservation(accountId, asset, amount));
    }

    @GetMapping("/wallets/{accountId}")
    public ResponseEntity<List<AssetWallet>> getWallets(@PathVariable String accountId) {
        return ResponseEntity.ok(custodyService.getWallets(accountId));
    }
}
