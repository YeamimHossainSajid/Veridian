# Real-Time Risk Engine & Margin Specification

## Purpose & Scope

The Veridian Risk Engine performs deterministic, sub-microsecond pre-trade risk validations, real-time Value-at-Risk (VaR) estimations, and automated market-wide or symbol-specific circuit breaker triggers.

---

## Pre-Trade Risk Checks

Every order submitted to the platform passes through synchronous pre-trade validation before entering the matching engine:

1. **Max Order Notional Check**: Rejects single orders with notional value exceeding preset tier limits:
   $$\text{Notional} = \text{Price} \times \text{Quantity} \le \text{MaxOrderNotional}$$
2. **Gross Portfolio Exposure Limit**:
   $$\sum_{i} |\text{Position}_i \times \text{MarkPrice}_i| \le \text{MaxGrossExposure}$$
3. **Daily Max Drawdown Circuit**: Real-time realized + unrealized loss cannot exceed the account's defined daily loss limit.
4. **Price Banding / Fat-Finger Protection**: Limit buy orders cannot be placed more than $X\%$ above best ask; limit sell orders cannot be placed more than $X\%$ below best bid.

---

## Dynamic Circuit Breakers

When excessive volatility is detected (e.g. price change $>10\%$ over a rolling 60-second window), the Risk Engine trips a symbol halt:
- Current order matching for that symbol is paused.
- Unfilled limit orders may be cancelled.
- Trading enters a 60-second cool-down call auction phase before continuous trading resumes.
