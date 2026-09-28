package com.veridian.custody.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "asset_wallets", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accountId", "asset"})
})
public class AssetWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String accountId;

    @Column(nullable = false, length = 16)
    private String asset;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal freeBalance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal lockedBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    public AssetWallet() {}

    public AssetWallet(String accountId, String asset, BigDecimal freeBalance, BigDecimal lockedBalance) {
        this.accountId = accountId;
        this.asset = asset;
        this.freeBalance = freeBalance;
        this.lockedBalance = lockedBalance;
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getAsset() { return asset; }
    public BigDecimal getFreeBalance() { return freeBalance; }
    public void setFreeBalance(BigDecimal freeBalance) { this.freeBalance = freeBalance; }
    public BigDecimal getLockedBalance() { return lockedBalance; }
    public void setLockedBalance(BigDecimal lockedBalance) { this.lockedBalance = lockedBalance; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
