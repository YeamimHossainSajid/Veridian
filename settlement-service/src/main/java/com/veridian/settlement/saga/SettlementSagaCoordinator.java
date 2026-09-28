package com.veridian.settlement.saga;

import com.veridian.settlement.model.SettlementBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface SettlementRepository extends JpaRepository<SettlementBatch, Long> {
    Optional<SettlementBatch> findByTradeId(String tradeId);
    List<SettlementBatch> findByStatus(SettlementBatch.SettlementStatus status);
}

@Service
public class SettlementSagaCoordinator {

    private final SettlementRepository settlementRepository;

    public SettlementSagaCoordinator(SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }

    @Transactional
    public SettlementBatch processTradeClearing(String tradeId, String buyer, String seller,
                                               BigDecimal grossAmount, BigDecimal feeAmount) {
        String batchId = "SETTLE-" + UUID.randomUUID();
        SettlementBatch batch = new SettlementBatch(
                batchId,
                tradeId,
                buyer,
                seller,
                grossAmount,
                feeAmount,
                SettlementBatch.SettlementStatus.COMMITTED
        );
        return settlementRepository.save(batch);
    }

    public List<SettlementBatch> getAllSettlements() {
        return settlementRepository.findAll();
    }
}
