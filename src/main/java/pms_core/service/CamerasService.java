package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.OwnersEntity;
import pms_core.dao.repository.CamerasRepository;
import pms_core.mapper.CameraMapper;
import pms_core.model.request.CameraRequest;
import pms_core.model.response.CameraResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CamerasService {

    private final CamerasRepository repository;
    private final CameraMapper mapper;

    @Transactional(readOnly = true)
    public List<CameraResponse> findAll(){
       return mapper.toResponse(repository.findAllCamera());
    }

    @Transactional
    public CameraResponse addCamera(CameraRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional
    public CameraResponse updateCamera(Integer id,CameraRequest request){
        CamerasEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setUpdated(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteCamera(Integer cameraId){

        repository.findById(cameraId)
                .map(camera -> {
                    camera.setActive(0);
                    return repository.save(camera);})
                .orElseThrow(() -> new RuntimeException("Camera not found or already inactive"));

        return "Camera deleted !";
    }

    @Transactional(readOnly = true)
    public CameraResponse findById(Integer cameraId){
        return mapper.toResponse(repository.findById(cameraId)
                .orElseThrow(() -> new RuntimeException("Camera not found or already inactive")));
    }
}
