# Veridian Developer Guide

## System Onboarding

Welcome to the Veridian development team. This document provides step-by-step instructions for developing, testing, and debugging microservices in the platform.

---

## Workspace Layout

```
├── common-lib/              # Shared protobuf, enums, events, and utilities
├── api-gateway/             # Spring Cloud Gateway routing and JWT filtering
├── service-registry/        # Eureka service registry discovery
├── order-service/           # Order ingestion, CQRS write store, Outbox publisher
├── matching-engine/         # Ultra-low-latency LMAX Disruptor order matching
├── risk-engine/             # Real-time pre-trade risk checks and circuit breakers
├── market-data-service/     # Level 2 order book broadcaster and tick generator
├── payment-service/         # Double-entry ledger for cash balances
├── custody-service/         # Asset reservation and multi-token balances
├── settlement-service/      # Distributed saga clearing and settlement
├── analytics-service/       # VWAP calculation and ClickHouse reporting
├── infrastructure/          # Docker Compose, K8s manifests, Terraform modules
├── observability/           # Prometheus alerts, Grafana dashboards
├── load-tests/              # Gatling and k6 performance scenarios
└── docs/                    # Architectural documents and runbooks
```

---

## Profiling Low Latency

When profiling hot paths in `matching-engine`:
1. Use **async-profiler** to capture flame graphs without safepoint bias:
   ```bash
   ./asprof -d 30 -f flamegraph.html <matching-engine-pid>
   ```
2. Verify allocation rate with JFR (Java Flight Recorder):
   ```bash
   -XX:StartFlightRecording=disk=true,dumponexit=true,filename=recording.jfr
   ```
3. Ensure zero object allocations occur inside the Disruptor event handler loop.
