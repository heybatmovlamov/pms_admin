package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.CamerasRepository;
import pms_core.mapper.CameraMapper;
import pms_core.model.request.CameraRequest;
import pms_core.model.response.CameraResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CamerasService {

    private final CamerasRepository repository;
    private final CameraMapper mapper;

    @Transactional(readOnly = true)
    public List<CameraResponse> findAll(){
       return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public CameraResponse addCamera(CameraRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public CameraResponse updateCamera(CameraRequest request){
//
//    }

    @Transactional
    public String deleteCamera(Integer cameraId){

        repository.findByIdAndStatusAndActive(cameraId, 1, 1)
                .map(camera -> {
                    camera.setActive(0);
                    return repository.save(camera);})
                .orElseThrow(() -> new RuntimeException("Camera not found or already inactive"));

        return "Camera deleted !";
    }

    @Transactional(readOnly = true)
    public CameraResponse findById(Integer cameraId){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(cameraId, 1, 1)
                .orElseThrow(() -> new RuntimeException("Camera not found or already inactive")));
    }
}
