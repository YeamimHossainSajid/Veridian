# Veridian FIX Protocol 4.4 / 5.0 Specification

## FIX Engine Gateway

For institutional algorithmic trading firms, proprietary market makers, and low-latency hedge funds, Veridian provides a native FIX (Financial Information eXchange) engine endpoint supporting FIX 4.4 and FIX 5.0 SP2.

---

## Supported Messages

| MsgType | Message Name | Direction | Description |
|---|---|---|---|
| `A` | Logon | Client $\rightarrow$ Veridian | Establishes session authentication |
| `0` | Heartbeat | Bidirectional | Verifies connection health |
| `D` | NewOrderSingle | Client $\rightarrow$ Veridian | Submits buy/sell order |
| `F` | OrderCancelRequest | Client $\rightarrow$ Veridian | Requests cancellation of active order |
| `8` | ExecutionReport | Veridian $\rightarrow$ Client | Order acknowledgment, fill, or rejection |
| `9` | OrderCancelReject | Veridian $\rightarrow$ Client | Rejection of cancel request |

---

## Sample FIX Message (NewOrderSingle)

```
8=FIX.4.4|9=142|35=D|49=HEDGE_FUND_A|56=VERIDIAN|34=101|52=20260928-07:30:00.123|11=CLORD1234|55=BTC-USD|54=1|38=1.5|40=2|44=64500.00|59=0|10=084|
```

- Tag 54: `1` (Buy)
- Tag 40: `2` (Limit)
- Tag 59: `0` (Day / GTC)
