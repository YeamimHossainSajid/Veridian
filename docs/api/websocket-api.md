# Veridian WebSocket Market Data & Order Streaming

## Connection URL
`ws://api.veridian.trade/ws/v1/marketdata`

---

## 1. Subscribe to Level 2 Order Book Depth

### Request
```json
{
  "action": "SUBSCRIBE",
  "channel": "orderbook_l2",
  "symbol": "BTC-USD"
}
```

### Stream Message (Sample)
```json
{
  "symbol": "BTC-USD",
  "sequenceNumber": 1420951,
  "timestampEpochMs": 1790581200000,
  "bids": [
    {"price": 64500.50, "quantity": 3.4500, "ordersCount": 4},
    {"price": 64500.00, "quantity": 12.1000, "ordersCount": 9}
  ],
  "asks": [
    {"price": 64501.00, "quantity": 2.1500, "ordersCount": 3},
    {"price": 64501.50, "quantity": 8.0000, "ordersCount": 6}
  ]
}
```

---

## 2. Heartbeat & Keep-Alive
Clients must transmit a ping frame or `{"action":"PING"}` at least once every 30 seconds. The server responds with `{"action":"PONG"}`.
