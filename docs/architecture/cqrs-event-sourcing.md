# CQRS and Event Sourcing in Veridian

## Why CQRS for Financial Trading?

In high-throughput electronic trading platforms, read patterns and write patterns have radically different performance, consistency, and throughput requirements:

- **Write Path (Commands)**: Requires strict transactional validity, sequence guarantees, credit risk checks, and deterministic ordering. High write concurrency on shared entities causes locking bottlenecks.
- **Read Path (Queries)**: Requires instantaneous sub-millisecond retrieval of book depth, position summaries, and execution history without blocking matching engines.

By decoupling the Command Stack from the Query Stack via CQRS (Command Query Responsibility Segregation), Veridian achieves maximum throughput without relational database read locks.

---

## Transactional Outbox Pattern

To prevent dual-write anomalies between PostgreSQL and Apache Kafka, Veridian implements the Transactional Outbox pattern:

1. When an order command is accepted, `OrderEntity` and `OutboxEvent` are written inside a single atomic database transaction.
2. A dedicated outbox poller streams events to the Kafka `order-events` topic using idempotent producers (`acks=all`).
3. Downstream services consume from Kafka with consumer group offsets ensuring at-least-once delivery.

```mermaid
sequenceDiagram
    autonumber
    Trader->>OrderService: POST /orders
    OrderService->>PostgreSQL: BEGIN TX; INSERT orders; INSERT outbox_events; COMMIT TX;
    OrderService-->>Trader: 201 Created (Order Placed)
    loop Background Outbox Publisher
        OrderService->>PostgreSQL: SELECT * FROM outbox_events WHERE processed = false
        OrderService->>Kafka: Publish to 'order-events'
        OrderService->>PostgreSQL: UPDATE outbox_events SET processed = true
    end
```

---

## Event Sourcing & Deterministic Replay

All state transitions in Veridian are represented as an ordered stream of domain events:
- `OrderPlacedEvent`
- `OrderValidatedEvent`
- `OrderMatchedEvent`
- `TradeExecutedEvent`
- `OrderCancelledEvent`

### State Rehydration
Should a matching engine node terminate unexpectedly, state is reconstructed deterministically by replaying events from Kafka starting at the last persisted snapshot offset.
