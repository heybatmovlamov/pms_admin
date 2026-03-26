package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.CustomersEntity;
import web_pms_core.dao.repository.CustomersRepository;
import web_pms_core.exception.DataNotFoundException;
import web_pms_core.mapper.CustomerMapper;
import web_pms_core.model.request.CustomerRequest;
import web_pms_core.model.response.CustomerResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersRepository repository;
    private final CustomerMapper mapper;

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return mapper.toResponse(repository.findAllActive());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Integer id) {
        CustomersEntity entity = repository.findById(id)
                .orElseThrow(() -> DataNotFoundException.of("Customer not found with id: {0}", id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public CustomerResponse add(CustomerRequest request) {
        CustomersEntity entity = mapper.toEntity(request);
        entity.setActive(1);
        entity.setStatus(1);
        entity.setCounter(0);
        entity.setKyc(request.getKyc() != null ? request.getKyc() : 0);
        entity.setCreated(LocalDateTime.now());
        entity.setModified(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public CustomerResponse update(Integer id, CustomerRequest request) {
        CustomersEntity entity = repository.findById(id)
                .orElseThrow(() -> DataNotFoundException.of("Customer not found with id: {0}", id));
        mapper.updateEntityFromRequest(request, entity);
        entity.setModified(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void softDelete(Integer id) {
        CustomersEntity entity = repository.findById(id)
                .orElseThrow(() -> DataNotFoundException.of("Customer not found with id: {0}", id));
        entity.setActive(0);
        repository.save(entity);
    }
}
