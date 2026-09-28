# Veridian Load Testing Suite

This module contains stress testing and load testing scripts designed to evaluate Veridian under extreme financial throughput conditions (up to 100,000 orders/sec).

## Test Tools

1. **k6**: Fast scriptable load testing for API Gateway and latency SLO verification.
   - Run: `k6 run k6/order_spike_test.js`
2. **Gatling**: High-concurrency scenario testing for order placement ramp-up.
   - Run: `./gradlew gatlingRun-com.veridian.loadtests.OrderSimulation`

## Latency Target SLOs

- **P50**: < 200 µs
- **P95**: < 500 µs
- **P99**: < 1.0 ms
- **Max Error Rate**: < 0.001%
