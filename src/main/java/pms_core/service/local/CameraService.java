package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.repository.CamerasRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraService {

    private final CamerasRepository cameraRepository;

    public CamerasEntity getCamera(String clientIp) {
        return cameraRepository.findByIp(clientIp)
                .orElseThrow(() -> new RuntimeException("Camera not found"));
    }
}


