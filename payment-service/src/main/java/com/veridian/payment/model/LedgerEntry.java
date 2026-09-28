package com.veridian.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "ledger_entries", indexes = {
    @Index(name = "idx_ledger_account", columnList = "accountId"),
    @Index(name = "idx_ledger_tx", columnList = "transactionRef")
})
public class LedgerEntry {

    public enum EntryType {
        DEBIT,
        CREDIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String transactionRef;

    @Column(nullable = false, length = 64)
    private String accountId;

    @Column(nullable = false, length = 16)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private EntryType entryType;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public LedgerEntry() {}

    public LedgerEntry(String transactionRef, String accountId, String currency,
                       EntryType entryType, BigDecimal amount, String description) {
        this.transactionRef = transactionRef;
        this.accountId = accountId;
        this.currency = currency;
        this.entryType = entryType;
        this.amount = amount;
        this.description = description;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getTransactionRef() { return transactionRef; }
    public String getAccountId() { return accountId; }
    public String getCurrency() { return currency; }
    public EntryType getEntryType() { return entryType; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
