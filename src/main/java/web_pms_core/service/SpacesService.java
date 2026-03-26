package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.SpacesEntity;
import web_pms_core.dao.repository.SpacesRepository;
import web_pms_core.mapper.SpaceMapper;
import web_pms_core.model.request.SpaceRequest;
import web_pms_core.model.response.SpaceResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpacesService {

    private final SpacesRepository repository;
    private final SpaceMapper mapper;

    @Transactional(readOnly = true)
    public List<SpaceResponse> findAll(){
        return mapper.toResponse(repository.findAllSpaces());
    }

    @Transactional
    public SpaceResponse addSpace(SpaceRequest request){
        SpacesEntity entity = mapper.toEntity(request);
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    public SpaceResponse updateSpace(Integer id,SpaceRequest request){
        SpacesEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setUpdated(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteSpace(Integer id){
        repository.findById(id)
                .map(space -> {
                    space.setActive(0);
                    return repository.save(space);})
                .orElseThrow(() -> new RuntimeException("Space not found or already inactive"));

        return "Space deleted !";
    }

    @Transactional(readOnly = true)
    public SpaceResponse findById(Integer id){
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found or already inactive")));
    }
}
