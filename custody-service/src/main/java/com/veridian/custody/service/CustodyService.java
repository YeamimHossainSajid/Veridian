package com.veridian.custody.service;

import com.veridian.custody.model.AssetWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
interface WalletRepository extends JpaRepository<AssetWallet, Long> {
    List<AssetWallet> findByAccountId(String accountId);
    Optional<AssetWallet> findByAccountIdAndAsset(String accountId, String asset);
}

@Service
public class CustodyService {

    private final WalletRepository walletRepository;

    public CustodyService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public AssetWallet reserveBalance(String accountId, String asset, BigDecimal amount) {
        AssetWallet wallet = walletRepository.findByAccountIdAndAsset(accountId, asset)
                .orElseGet(() -> walletRepository.save(new AssetWallet(accountId, asset, new BigDecimal("1000000.00"), BigDecimal.ZERO)));

        if (wallet.getFreeBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient free balance to reserve: " + amount);
        }

        wallet.setFreeBalance(wallet.getFreeBalance().subtract(amount));
        wallet.setLockedBalance(wallet.getLockedBalance().add(amount));
        wallet.setUpdatedAt(Instant.now());
        return walletRepository.save(wallet);
    }

    @Transactional
    public AssetWallet releaseReservation(String accountId, String asset, BigDecimal amount) {
        AssetWallet wallet = walletRepository.findByAccountIdAndAsset(accountId, asset)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        wallet.setLockedBalance(wallet.getLockedBalance().subtract(amount));
        wallet.setFreeBalance(wallet.getFreeBalance().add(amount));
        wallet.setUpdatedAt(Instant.now());
        return walletRepository.save(wallet);
    }

    public List<AssetWallet> getWallets(String accountId) {
        return walletRepository.findByAccountId(accountId);
    }
}
