# Veridian REST API Specification

## Base URL
`http://api.veridian.trade/api/v1`

---

## 1. Place Order
- **Method**: `POST`
- **Path**: `/orders`
- **Headers**: `Content-Type: application/json`, `Authorization: Bearer <JWT>`

### Request Body
```json
{
  "accountId": "ACC-9042",
  "symbol": "BTC-USD",
  "side": "BUY",
  "orderType": "LIMIT",
  "price": 64500.50,
  "quantity": 1.2500,
  "timeInForce": "GTC"
}
```

### Response Body (`201 Created`)
```json
{
  "orderId": "ORD-7f41a808-f4cb-4d40-9d0a",
  "accountId": "ACC-9042",
  "symbol": "BTC-USD",
  "side": "BUY",
  "orderType": "LIMIT",
  "price": 64500.50,
  "quantity": 1.2500,
  "executedQuantity": 0.0000,
  "timeInForce": "GTC",
  "status": "PENDING_RISK_CHECK",
  "createdAt": "2026-09-28T07:30:00.000Z"
}
```

---

## 2. Get Order
- **Method**: `GET`
- **Path**: `/orders/{orderId}`

---

## 3. Account Ledger
- **Method**: `GET`
- **Path**: `/payments/ledger/{accountId}`

---

## 4. Market Metrics
- **Method**: `GET`
- **Path**: `/analytics/metrics/{symbol}`
