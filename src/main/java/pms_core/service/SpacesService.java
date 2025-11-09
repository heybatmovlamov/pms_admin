package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.SpacesRepository;
import pms_core.mapper.SpaceMapper;
import pms_core.model.request.SpaceRequest;
import pms_core.model.response.SpaceResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpacesService {

    private final SpacesRepository repository;
    private final SpaceMapper mapper;

    @Transactional(readOnly = true)
    public List<SpaceResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public SpaceResponse addSpace(SpaceRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public SpaceResponse updateSpace(SpaceRequest request){
//
//    }

    @Transactional
    public String deleteSpace(Integer id){
        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(space -> {
                    space.setActive(0);
                    return repository.save(space);})
                .orElseThrow(() -> new RuntimeException("Space not found or already inactive"));

        return "Space deleted !";
    }

    @Transactional(readOnly = true)
    public SpaceResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Space not found or already inactive")));
    }
}
