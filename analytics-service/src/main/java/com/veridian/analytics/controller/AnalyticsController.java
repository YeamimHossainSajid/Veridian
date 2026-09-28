package com.veridian.analytics.controller;

import com.veridian.analytics.model.TradeMetric;
import com.veridian.analytics.service.VolumeAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final VolumeAnalyticsService volumeAnalyticsService;

    public AnalyticsController(VolumeAnalyticsService volumeAnalyticsService) {
        this.volumeAnalyticsService = volumeAnalyticsService;
    }

    @GetMapping("/metrics/{symbol}")
    public ResponseEntity<TradeMetric> getMetrics(@PathVariable String symbol) {
        return ResponseEntity.ok(volumeAnalyticsService.getMetricsForSymbol(symbol));
    }
}
