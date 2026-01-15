package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.dao.repository.VehiclesRepository;
import pms_core.model.local.request.CameraPayload;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehiclesRepository vehicleRepository;

    public VehiclesEntity getActiveVehicleByPlate(CameraPayload cameraPayload) {
        var optional = vehicleRepository.findFirstByPlateAndActiveOrderByCreatedDesc(cameraPayload.getPlate(), 1);
        optional.ifPresent(this::vehicleReset);
        return optional.get();
    }

    public void vehicleReset(VehiclesEntity entity) {
        if (entity != null) {
            log.info("Vehicle already entered. Update status....");
            log.info("Vehicle RESET....");
            entity.setActive(0);
            entity.setStatus(4);
            vehicleRepository.save(entity);
            log.info("Status updated as Skipped.");
        }
    }

    public VehiclesEntity addVehicle(String plate,
                           String path,
                           LocalDateTime dateTime,
                           String vehicleAction,
                           Integer tariffId,
                           Integer tariffDurationStart,
                           CamerasEntity camera) {

        VehiclesEntity entity = VehiclesEntity.builder()
                .plate(plate)
                .tariff(tariffId)
                .scanned(dateTime)
                .created(LocalDateTime.now())
                .paid(dateTime.plusMinutes(tariffDurationStart))
                .type("DEFAULT")
                .owner(0)
                .organization(camera.getOrganization())
                .parking(camera.getParking())
                .space(camera.getSpace())
                .brand(camera.getName() + ":" + camera.getIp())
                .color("---")
                .action(path.toUpperCase())
                .description(plate + " : " + vehicleAction)
                .active(1)
                .status(1)
                .build();

        return vehicleRepository.save(entity);
    }

}

