package pms_core.dao.repository.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pms_core.dao.entity.TransactionsEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionsRepository extends CrudRepository<TransactionsEntity, Integer>, PagingAndSortingRepository<TransactionsEntity, Integer>, CustomTransactionRepository {
    Optional<TransactionsEntity> findFirstByTxnIdAndStatus(String txnId, Integer status);

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE vehicle = :vehicleId AND status = 1")
    BigDecimal sumAmountByVehicleIdAndStatus(@Param("vehicleId") Integer vehicleId);

    Page<TransactionsEntity> findByDatetimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<TransactionsEntity> findByDatetimeGreaterThanEqual(LocalDateTime start, Pageable pageable);

    List<TransactionsEntity> findByDatetimeGreaterThanEqual(LocalDateTime start);

    Page<TransactionsEntity> findByDatetimeLessThanEqual(LocalDateTime end, Pageable pageable);
}
