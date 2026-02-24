package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.TransactionsEntity;
import pms_core.dao.repository.TransactionsRepository;
import pms_core.mapper.TransactionMapper;
import pms_core.model.request.TransactionListRequest;
import pms_core.model.response.TransactionResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository repository;
    private final TransactionMapper mapper;

    @Transactional(readOnly = true)
    public Page<TransactionResponse> findAll(TransactionListRequest request) {
        if (request == null) {
            request = TransactionListRequest.builder().build();
        }
        Pageable pageable = toPageable(request);
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;

        Page<TransactionsEntity> entityPage;
        if (start != null && end != null) {
            entityPage = repository.findByDatetimeBetween(start, end, pageable);
        } else if (start != null) {
            entityPage = repository.findByDatetimeGreaterThanEqual(start, pageable);
        } else if (end != null) {
            entityPage = repository.findByDatetimeLessThanEqual(end, pageable);
        } else {
            entityPage = repository.findAll(pageable);
        }

        List<TransactionResponse> content = mapper.toResponse(entityPage.getContent());
        return new PageImpl<>(content, entityPage.getPageable(), entityPage.getTotalElements());
    }

    private static Pageable toPageable(TransactionListRequest request) {
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 20;
        Sort sort = Sort.by(Sort.Direction.DESC, "datetime");
        return PageRequest.of(page, size, sort);
    }
}
