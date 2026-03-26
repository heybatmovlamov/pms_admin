package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.repository.CamerasRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraService {

    private final CamerasRepository cameraRepository;

    public Optional<CamerasEntity> findCamera(String clientIp) {
        return cameraRepository.findByIp(clientIp);
    }
}


