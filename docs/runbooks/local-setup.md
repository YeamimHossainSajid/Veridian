# Local Development Runbook

## Prerequisites
- JDK 17 or higher
- Docker & Docker Compose
- Gradle 8+ (or Gradle wrapper)
- k6 (for load testing)

---

## 1. Start Infrastructure Stack
Launch Kafka, PostgreSQL, Redis, ClickHouse, Prometheus, and Grafana:

```bash
cd infrastructure
docker-compose --env-file docker-compose.env up -d
```

Verify running containers:
```bash
docker-compose ps
```

---

## 2. Build the Microservices
From the project root:

```bash
./gradlew build -x test
```

---

## 3. Run Microservices

Start services in separate terminal sessions or background processes:
```bash
# 1. API Gateway
./gradlew :api-gateway:bootRun

# 2. Order Service
./gradlew :order-service:bootRun

# 3. Matching Engine
./gradlew :matching-engine:bootRun

# 4. Risk Engine
./gradlew :risk-engine:bootRun

# 5. Market Data Service
./gradlew :market-data-service:bootRun
```

---

## 4. Run Stress Test
```bash
k6 run load-tests/k6/order_spike_test.js
```
