package com.veridian.payment.service;

import com.veridian.payment.model.LedgerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentLedgerServiceTest {

    @Mock
    private LedgerRepository ledgerRepository;

    private PaymentLedgerService service;

    @BeforeEach
    void setUp() {
        service = new PaymentLedgerService(ledgerRepository);
    }

    @Test
    @DisplayName("Should create credit entry on deposit")
    void testRecordDeposit() {
        when(ledgerRepository.save(any(LedgerEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal amount = new BigDecimal("5000.00");
        LedgerEntry entry = service.recordDeposit("ACC-1", "USD", amount);

        assertNotNull(entry);
        assertEquals("ACC-1", entry.getAccountId());
        assertEquals("USD", entry.getCurrency());
        assertEquals(LedgerEntry.EntryType.CREDIT, entry.getEntryType());
        assertEquals(amount, entry.getAmount());
        assertTrue(entry.getTransactionRef().startsWith("DEP-"));

        verify(ledgerRepository, times(1)).save(any(LedgerEntry.class));
    }

    @Test
    @DisplayName("Should create debit entry on withdrawal")
    void testRecordWithdrawal() {
        when(ledgerRepository.save(any(LedgerEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal amount = new BigDecimal("250.50");
        LedgerEntry entry = service.recordWithdrawal("ACC-2", "EUR", amount);

        assertNotNull(entry);
        assertEquals("ACC-2", entry.getAccountId());
        assertEquals("EUR", entry.getCurrency());
        assertEquals(LedgerEntry.EntryType.DEBIT, entry.getEntryType());
        assertEquals(amount, entry.getAmount());
        assertTrue(entry.getTransactionRef().startsWith("WTH-"));

        verify(ledgerRepository, times(1)).save(any(LedgerEntry.class));
    }

    @Test
    @DisplayName("Should retrieve ledger entries for an account")
    void testGetAccountEntries() {
        List<LedgerEntry> mockList = List.of(
                new LedgerEntry("DEP-1", "ACC-3", "USD", LedgerEntry.EntryType.CREDIT, new BigDecimal("1000.00"), "Initial deposit")
        );
        when(ledgerRepository.findByAccountId("ACC-3")).thenReturn(mockList);

        List<LedgerEntry> result = service.getAccountEntries("ACC-3");
        assertEquals(1, result.size());
        assertEquals("ACC-3", result.get(0).getAccountId());
    }
}
