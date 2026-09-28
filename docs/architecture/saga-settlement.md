# Distributed Settlement & Saga Orchestration

## Overview

Unlike traditional financial markets with T+2 or T+1 settlement cycles, Veridian executes atomic clearing and settlement with simulated continuous gross settlement (T+0).

Because transactions cross multiple microservice boundaries (`matching-engine`, `custody-service`, and `settlement-service`), distributed transactions are coordinated via the **Saga Pattern**.

---

## Saga Workflow

```mermaid
sequenceDiagram
    autonumber
    MatchingEngine->>SettlementService: TradeExecutedEvent
    SettlementService->>CustodyService: ReserveBuyerCash(grossAmount)
    alt Cash Reservation Success
        SettlementService->>CustodyService: ReserveSellerAsset(quantity)
        alt Asset Reservation Success
            SettlementService->>CustodyService: CommitTransfers()
            SettlementService->>SettlementService: MarkSettlementCommitted()
        else Asset Reservation Failed
            SettlementService->>CustodyService: RollbackBuyerCash()
            SettlementService->>SettlementService: MarkSettlementFailed()
        end
    else Cash Reservation Failed
        SettlementService->>SettlementService: MarkSettlementFailed()
    end
```

---

## Compensating Transactions

Every step in the clearing saga defines an explicit compensating transaction:
- `ReserveBuyerCash` $\rightarrow$ `RollbackBuyerCash`
- `ReserveSellerAsset` $\rightarrow$ `RollbackSellerAsset`

In the event of network timeouts or system partition, uncommitted sagas are automatically audited and reconciled by background recovery workers.
