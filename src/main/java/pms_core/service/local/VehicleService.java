package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.dao.repository.VehiclesRepository;
import pms_core.model.local.request.CameraPayload;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehiclesRepository vehicleRepository;

    public Optional<VehiclesEntity> findActiveVehicleByPlate(String plate) {
        return vehicleRepository.findFirstByPlateAndActiveAndStatusOrderByCreatedDesc(plate, 1, 1);
    }

    public VehiclesEntity updateVehicle(VehiclesEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void ifVehicleEntered(VehiclesEntity entity) {
        if (entity != null) {
            log.info("Vehicle already entered. Update status....");
            log.info("Vehicle RESET....");
            entity.setActive(0);
            entity.setStatus(4);
            vehicleRepository.save(entity);
            log.info("Status updated as Skipped.");
        }
    }

    public VehiclesEntity addVehicleIn(String path,
                                       String plate,
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

    public VehiclesEntity addVehicleOut(String path,
                                        String plate,
                                        Integer tariffId,
                                        Integer ownerId,
                                        LocalDateTime dateTime,
                                        CamerasEntity camera,
                                        Integer active,
                                        Integer status) {


        VehiclesEntity entity = VehiclesEntity.builder()
                .plate(plate)
                .tariff(tariffId)
                .scanned(dateTime)
                .exit(LocalDateTime.now())
                .type("DEFAULT")
                .owner(ownerId)
                .organization(camera.getOrganization())
                .parking(camera.getParking())
                .space(camera.getSpace())
                .brand("---")
                .color(camera.getName() + ":" + camera.getIp())
                .action(path.toUpperCase())
                .description(plate + " : " + dateTime)
                .active(active)
                .status(status)
                .build();

        return vehicleRepository.save(entity);
    }

    public VehiclesEntity updateVehicleAsExit(VehiclesEntity vehicle, CamerasEntity camera, Integer ownerId) {
        vehicle.setExit(LocalDateTime.now());
        vehicle.setAction("OUT");
        vehicle.setActive(0);
        vehicle.setStatus(3);
        vehicle.setColor(camera.getName() + ":" + camera.getIp());
        if (ownerId != null) {
            vehicle.setOwner(ownerId);
        }
        return vehicleRepository.save(vehicle);
    }

}

