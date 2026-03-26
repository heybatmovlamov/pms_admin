package pms_core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.repository.OrganizationsRepository;
import pms_core.mapper.OrganizationMapper;
import pms_core.model.request.OrganizationRequest;
import pms_core.model.response.OrganizationResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationsService {

    private final OrganizationMapper mapper;
    private final OrganizationsRepository repository;

    @Transactional(readOnly = true)
    public List<OrganizationResponse> findAll(){
        return mapper.toResponse(repository.findAllOrganizations());
    }

    @Transactional
    public OrganizationResponse addOrganization(OrganizationRequest request){
        OrganizationsEntity entity = mapper.toEntity(request);
        entity.setStatus(1);
        entity.setCreated(LocalDateTime.now());
        entity.setModified(LocalDateTime.now());
        log.info(entity.toString());
        return mapper.toResponse(repository.save(entity));
    }

    public OrganizationResponse updateOrganization(Integer id, OrganizationRequest request) {
        OrganizationsEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setModified(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteOrganization(Integer id){
        repository.findById(id)
                .map(organizations -> {
                    organizations.setStatus(0);
                    return repository.save(organizations);})
                .orElseThrow(() -> new RuntimeException("Organization not found or already inactive"));

        return "Organization deleted !";
    }

    @Transactional(readOnly = true)
    public OrganizationResponse findById(Integer id){
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found or already inactive")));
    }
}
