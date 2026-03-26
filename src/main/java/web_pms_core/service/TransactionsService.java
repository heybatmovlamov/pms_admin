package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.ClosingEntity;
import web_pms_core.dao.entity.TransactionsEntity;
import web_pms_core.dao.repository.ClosingTimeRepository;
import web_pms_core.dao.repository.transactions.TransactionsRepository;
import web_pms_core.mapper.TransactionMapper;
import web_pms_core.model.request.TransactionListRequest;
import web_pms_core.model.response.PageResponse;
import web_pms_core.model.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository repository;
    private final TransactionMapper mapper;
    private final ClosingTimeRepository closingTimeRepository;

    @Transactional(readOnly = true)
    public Page<TransactionResponse> findAll(TransactionListRequest request) {
        if (request == null) {
            request = TransactionListRequest.builder().build();
        }
        Pageable pageable = toPageable(request);

        PageResponse entities = repository.findAllSearching(request);
        List<TransactionResponse> content = mapper.toResponse(entities.getEntities());

        return new PageImpl<>(content, pageable, entities.getTotal());
    }

    public List<TransactionResponse> findEndDayOfTransactions() {
        ClosingEntity e = closingTimeRepository.findByDescription("DAY_END").orElse(null);
        log.info("Closing date is {}", e);
        LocalDateTime closingDateTime;
        if (e == null) {
            closingDateTime = LocalDateTime.now().toLocalDate().atStartOfDay();
            e = new ClosingEntity();
            e.setDescription("DAY_END");
        } else  {
            if (null != e.getClosingTime()) {
                closingDateTime = e.getClosingTime();
            }else{
                closingDateTime = LocalDateTime.now().toLocalDate().atStartOfDay();
            }
        }
        List<TransactionsEntity> transactions = repository.findByDatetimeGreaterThanEqual(closingDateTime);

        e.setClosingTime(LocalDateTime.now());
        closingTimeRepository.save(e);
        return  mapper.toResponse(transactions);
    }

    private static Pageable toPageable(TransactionListRequest request) {
        int page = request.getOffset() != null ? request.getOffset() : 0;
        int size = request.getLimit() != null ? request.getLimit() : 20;
        Sort sort = Sort.by(Sort.Direction.DESC, "datetime");
        return PageRequest.of(page, size, sort);
    }
}
