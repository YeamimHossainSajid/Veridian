package com.veridian.custody.service;

import com.veridian.custody.model.AssetWallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustodyServiceTest {

    @Mock
    private WalletRepository walletRepository;

    private CustodyService service;

    @BeforeEach
    void setUp() {
        service = new CustodyService(walletRepository);
    }

    @Test
    @DisplayName("Should successfully reserve balance when sufficient free balance exists")
    void testReserveBalanceSuccess() {
        AssetWallet wallet = new AssetWallet("ACC-1", "BTC", new BigDecimal("10.0"), BigDecimal.ZERO);
        when(walletRepository.findByAccountIdAndAsset("ACC-1", "BTC")).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(AssetWallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AssetWallet updated = service.reserveBalance("ACC-1", "BTC", new BigDecimal("2.5"));

        assertEquals(new BigDecimal("7.5"), updated.getFreeBalance());
        assertEquals(new BigDecimal("2.5"), updated.getLockedBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    @DisplayName("Should throw exception when reserving more than free balance")
    void testReserveBalanceInsufficientFunds() {
        AssetWallet wallet = new AssetWallet("ACC-1", "ETH", new BigDecimal("1.0"), BigDecimal.ZERO);
        when(walletRepository.findByAccountIdAndAsset("ACC-1", "ETH")).thenReturn(Optional.of(wallet));

        assertThrows(IllegalStateException.class, () ->
                service.reserveBalance("ACC-1", "ETH", new BigDecimal("5.0"))
        );
        verify(walletRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully release locked balance back to free balance")
    void testReleaseReservationSuccess() {
        AssetWallet wallet = new AssetWallet("ACC-2", "USD", new BigDecimal("1000.00"), new BigDecimal("500.00"));
        when(walletRepository.findByAccountIdAndAsset("ACC-2", "USD")).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(AssetWallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AssetWallet updated = service.releaseReservation("ACC-2", "USD", new BigDecimal("300.00"));

        assertEquals(new BigDecimal("1300.00"), updated.getFreeBalance());
        assertEquals(new BigDecimal("200.00"), updated.getLockedBalance());
    }

    @Test
    @DisplayName("Should throw exception when releasing non-existent wallet")
    void testReleaseReservationNotFound() {
        when(walletRepository.findByAccountIdAndAsset("ACC-UNKNOWN", "USD")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                service.releaseReservation("ACC-UNKNOWN", "USD", new BigDecimal("100.00"))
        );
    }
}
