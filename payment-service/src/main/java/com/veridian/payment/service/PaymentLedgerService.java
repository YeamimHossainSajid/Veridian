package com.veridian.payment.service;

import com.veridian.payment.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByAccountId(String accountId);
}

@Service
public class PaymentLedgerService {

    private final LedgerRepository ledgerRepository;

    public PaymentLedgerService(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Transactional
    public LedgerEntry recordDeposit(String accountId, String currency, BigDecimal amount) {
        String txRef = "DEP-" + UUID.randomUUID();
        LedgerEntry entry = new LedgerEntry(txRef, accountId, currency, LedgerEntry.EntryType.CREDIT, amount, "Deposit to account");
        return ledgerRepository.save(entry);
    }

    @Transactional
    public LedgerEntry recordWithdrawal(String accountId, String currency, BigDecimal amount) {
        String txRef = "WTH-" + UUID.randomUUID();
        LedgerEntry entry = new LedgerEntry(txRef, accountId, currency, LedgerEntry.EntryType.DEBIT, amount, "Withdrawal from account");
        return ledgerRepository.save(entry);
    }

    public List<LedgerEntry> getAccountEntries(String accountId) {
        return ledgerRepository.findByAccountId(accountId);
    }
}
