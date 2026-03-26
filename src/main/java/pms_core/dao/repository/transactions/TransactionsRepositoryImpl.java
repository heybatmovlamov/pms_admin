package pms_core.dao.repository.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pms_core.dao.entity.TransactionsEntity;
import pms_core.model.request.TransactionListRequest;
import pms_core.model.response.PageResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TransactionsRepositoryImpl implements CustomTransactionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageResponse findAllSearching(TransactionListRequest request) {

        if (request == null) {
            request = TransactionListRequest.builder().build();
        }

        LocalDateTime start = request.getStartDate() != null
                ? request.getStartDate().atStartOfDay()
                : null;

        LocalDateTime end = request.getEndDate() != null
                ? request.getEndDate().atTime(23, 59, 59, 999_999_999)
                : null;

        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM transactions WHERE 1=1");

        Map<String, Object> params = new HashMap<>();

        if (start != null) {
            sql.append(" AND datetime >= :start");
            countSql.append(" AND datetime >= :start");
            params.put("start", start);
        }

        if (end != null) {
            sql.append(" AND datetime <= :end");
            countSql.append(" AND datetime <= :end");
            params.put("end", end);
        }

        if (request.getMethod() != null) {
            sql.append(" AND method = :method");
            countSql.append(" AND method = :method");
            params.put("method", request.getMethod());
        }

        // pagination
        sql.append(" ORDER BY datetime DESC");
        sql.append(" LIMIT :limit OFFSET :offset");

        params.put("limit", request.getLimit());
        params.put("offset", request.getOffset());

        List<TransactionsEntity> list = namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                new BeanPropertyRowMapper<>(TransactionsEntity.class)
        );

        Long total = namedParameterJdbcTemplate.queryForObject(
                countSql.toString(),
                params,
                Long.class
        );

        return new PageResponse(list,total);
    }
}
