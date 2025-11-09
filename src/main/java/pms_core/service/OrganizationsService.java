package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.OrganizationsRepository;
import pms_core.mapper.OrganizationMapper;
import pms_core.model.request.OrganizationRequest;
import pms_core.model.response.OrganizationResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationsService {

    private final OrganizationMapper mapper;
    private final OrganizationsRepository repository;

    @Transactional(readOnly = true)
    public List<OrganizationResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public OrganizationResponse addOrganization(OrganizationRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public OrganizationResponse updateOrganization(OrganizationRequest request){
//
//    }

    @Transactional
    public String deleteOrganization(Integer id){
        repository.findByIdAndStatus(id, 1)
                .map(organizations -> {
                    organizations.setStatus(0);
                    return repository.save(organizations);})
                .orElseThrow(() -> new RuntimeException("Organization not found or already inactive"));

        return "Organization deleted !";
    }

    @Transactional(readOnly = true)
    public OrganizationResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatus(id, 1)
                .orElseThrow(() -> new RuntimeException("Organization not found or already inactive")));
    }
}
