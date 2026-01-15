package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.OwnersEntity;
import pms_core.dao.repository.OwnersRepository;
import pms_core.mapper.OwnerMapper;
import pms_core.model.request.OwnerRequest;
import pms_core.model.response.OwnerResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnersService {

    private final OwnersRepository repository;
    private final OwnerMapper mapper;

    @Transactional(readOnly = true)
    public List<OwnerResponse> findAll(){
        return mapper.toResponse(repository.findAllOwners());
    }

    @Transactional
    public OwnerResponse addOwner(OwnerRequest request){
        OwnersEntity entity = mapper.toEntity(request);
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public OwnerResponse updateOwner(Integer id,OwnerRequest request){
        OwnersEntity entity = repository.findByIdAndStatusAndActive(id,1,1)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setUpdated(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteOwner(Integer id){
        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(owner -> {
                    owner.setActive(0);
                    return repository.save(owner);})
                .orElseThrow(() -> new RuntimeException("Owner not found or already inactive"));
        return "Owner deleted !";
    }

    @Transactional(readOnly = true)
    public OwnerResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Owner not found or already inactive")));
    }
}
