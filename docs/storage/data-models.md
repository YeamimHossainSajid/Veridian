# Polyglot Storage and Data Schemas

## 1. PostgreSQL (Transactional Command Store)

### `orders` Table
```sql
CREATE TABLE orders (
    order_id VARCHAR(64) PRIMARY KEY,
    account_id VARCHAR(64) NOT NULL,
    symbol VARCHAR(32) NOT NULL,
    side VARCHAR(16) NOT NULL,
    order_type VARCHAR(16) NOT NULL,
    price NUMERIC(19, 4),
    quantity NUMERIC(19, 4) NOT NULL,
    executed_quantity NUMERIC(19, 4) NOT NULL DEFAULT 0,
    time_in_force VARCHAR(16) NOT NULL DEFAULT 'GTC',
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_orders_account ON orders (account_id);
CREATE INDEX idx_orders_symbol ON orders (symbol);
CREATE INDEX idx_orders_status ON orders (status);
```

### `outbox_events` Table
```sql
CREATE TABLE outbox_events (
    id BIGSERIAL PRIMARY KEY,
    aggregate_type VARCHAR(64) NOT NULL,
    aggregate_id VARCHAR(64) NOT NULL,
    event_type VARCHAR(64) NOT NULL,
    payload TEXT NOT NULL,
    processed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_outbox_processed ON outbox_events (processed, created_at);
```

---

## 2. ClickHouse (Analytical Columnar Store)

### `trades_analytical` Table
```sql
CREATE TABLE veridian_analytics.trades_analytical (
    trade_id String,
    order_id String,
    account_id LowCardinality(String),
    counterparty_account_id LowCardinality(String),
    symbol LowCardinality(String),
    executed_price Decimal(18, 4),
    executed_quantity Decimal(18, 4),
    fee Decimal(18, 4),
    executed_at DateTime64(6, 'UTC')
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(executed_at)
ORDER BY (symbol, executed_at, trade_id);
```

---

## 3. Redis In-Memory Projections
- `book:{symbol}:bids`: Sorted set with score = price.
- `book:{symbol}:asks`: Sorted set with score = price.
- `account:{id}:risk`: Hash map storing margin, cash, and active order counts.
