package com.veridian.settlement.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "settlement_batches", indexes = {
    @Index(name = "idx_settle_status", columnList = "status"),
    @Index(name = "idx_settle_trade", columnList = "tradeId")
})
public class SettlementBatch {

    public enum SettlementStatus {
        PENDING,
        COMMITTED,
        FAILED,
        COMPENSATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String batchId;

    @Column(nullable = false, length = 64)
    private String tradeId;

    @Column(nullable = false, length = 64)
    private String buyerAccountId;

    @Column(nullable = false, length = 64)
    private String sellerAccountId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal grossAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal feeAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SettlementStatus status;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public SettlementBatch() {}

    public SettlementBatch(String batchId, String tradeId, String buyerAccountId,
                           String sellerAccountId, BigDecimal grossAmount,
                           BigDecimal feeAmount, SettlementStatus status) {
        this.batchId = batchId;
        this.tradeId = tradeId;
        this.buyerAccountId = buyerAccountId;
        this.sellerAccountId = sellerAccountId;
        this.grossAmount = grossAmount;
        this.feeAmount = feeAmount;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getBatchId() { return batchId; }
    public String getTradeId() { return tradeId; }
    public String getBuyerAccountId() { return buyerAccountId; }
    public String getSellerAccountId() { return sellerAccountId; }
    public BigDecimal getGrossAmount() { return grossAmount; }
    public BigDecimal getFeeAmount() { return feeAmount; }
    public SettlementStatus getStatus() { return status; }
    public void setStatus(SettlementStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
}
