# Veridian – Distributed High-Frequency Trading Platform

Production-grade event-driven trading platform built with **Java 21**, **Spring Boot 4.0.2**, and **Spring Cloud 2024**.

## First three services (current scope)

| Service           | Port | Description |
|-------------------|------|-------------|
| **Service Registry** | 8761 | Netflix Eureka – service discovery |
| **API Gateway**      | 8080 | Spring Cloud Gateway – JWT validation, rate limiting, BFF routing |
| **Order Service**    | 8081 | Orders – CQRS, Event Sourcing, Transactional Outbox, PostgreSQL, Kafka |

## Build (Gradle)

From the repo root, with Java 21 and Gradle installed:

```bash
gradle wrapper   # one-time: create ./gradlew
./gradlew build
```

Build individual services:

```bash
./gradlew :service-registry:bootJar
./gradlew :api-gateway:bootJar
./gradlew :order-service:bootJar
```

## Run locally

1. **Service Registry** (start first)
   ```bash
   ./gradlew :service-registry:bootRun
   ```
   Dashboard: http://localhost:8761

2. **API Gateway**
   ```bash
   ./gradlew :api-gateway:bootRun
   ```
   Set `JWT_SECRET` (min 256 bits for HS256) if you use JWT validation.

3. **Order Service** (needs PostgreSQL and Kafka)
   - PostgreSQL: create DB `veridian_orders`, set `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD` if not default.
   - Kafka: `KAFKA_BOOTSTRAP_SERVERS` (default `localhost:9092`).
   ```bash
   ./gradlew :order-service:bootRun
   ```

## Docker (per-service)

Build from repo root:

```bash
docker build -f service-registry/Dockerfile .
docker build -f api-gateway/Dockerfile .
docker build -f order-service/Dockerfile .
```

## Order Service – design

- **Domain**: `OrderAggregate` (root), value objects (`OrderId`, `Symbol`, `Side`, `Quantity`, `Price`, `OrderStatus`).
- **CQRS**: Command handler (`OrderCommandHandler`), query handler (`OrderQueryHandler`), separate read/write ports.
- **Event Sourcing**: Domain events (`OrderCreatedEvent`, `OrderUpdatedEvent`, `OrderCancelledEvent`) and outbox.
- **Transactional Outbox**: Events written to `outbox` in the same transaction as the aggregate; `OutboxProcessor` publishes to Kafka and deletes.
- **REST**: `POST /orders`, `GET /orders/{id}`, `PATCH /orders/{id}`, `DELETE /orders/{id}`.
- **Kafka**: Topic `order-events`; event types `order-created`, `order-updated`, `order-cancelled`.
- **Errors**: RFC 7807 `ProblemDetail` via `GlobalExceptionHandler`.
- **Observability**: Actuator health, Prometheus metrics, structured logging.

## Next services (to be implemented)

- Matching Engine, Market Data, Risk, Portfolio, Compliance, User, Wallet, Kafka Event Bus, Kafka Streams Processor.
