package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.OwnersEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.exception.BadRequestException;
import pms_core.exception.BlockedException;
import pms_core.exception.CameraNotFoundException;
import pms_core.exception.CameraScanException;
import pms_core.exception.NotPaidException;
import pms_core.model.local.request.CameraPayload;
import pms_core.model.local.response.CcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ManageService {

    private static final DateTimeFormatter CC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final CameraFunctionService cameraFunctionService;
    private final CameraService cameraService;
    private final PanelService panelService;
    private final OwnerService ownerService;
    private final VehicleService vehicleService;
    private final TariffService tariffService;
    private final SCService scService;

    public CcResult info() {
        return CcResult.builder().result("READY").build();
    }

    public CcResult in(String clientIp, String request) {
        log.info("IN request started");

        log.info("Find camera data ip : " + clientIp);
        CamerasEntity camera = cameraService.findCamera(clientIp)
                .orElseThrow(CameraNotFoundException::new);

        log.info("Find payload by type : "+camera.getType());
        CameraPayload payload = cameraFunctionService.parsePayload(request, camera.getType());

        log.info("Validation plate and date");
        validatePlate(camera, payload);
        validateDate(payload);

        String plate = payload.getPlate();
        if (ownerService.isBlocked(plate)) {
            log.info("CC IN: plate {} is BLOCKED", plate);
            panelService.print(camera.getDescription(), "BLOCKED");
            throw new BlockedException();
        }

        Optional<VehiclesEntity> existingVehicle = vehicleService.findActiveVehicleByPlate(plate);
        existingVehicle.ifPresent(vehicleService::ifVehicleEntered);

        LocalDateTime dateTime = parseDate(payload.getDateTime());
        TariffsEntity tariff = tariffService.getTariff(camera, dateTime);

        VehiclesEntity entered = vehicleService.addVehicleIn(
                "IN",
                plate,
                dateTime,
                "IN",
                tariff.getId(),
                tariff.getDurationStart(),
                camera
        );

        panelService.print(camera.getDescription(), "KECIN");
        cameraFunctionService.openDoor(camera.getType(), clientIp);

        log.info("CC IN success: {}", entered);

        return CcResult.builder()
                .result("entered")
                .plate(entered.getPlate())
                .scanned(formatDateTime(entered.getScanned()))
                .created(formatDateTime(entered.getCreated()))
                .tariff(String.valueOf(entered.getTariff()))
                .action(entered.getAction())
                .build();
    }

    public CcResult out(String clientIp, String request) {
        CamerasEntity camera = cameraService.findCamera(clientIp)
                .orElseThrow(CameraNotFoundException::new);

        CameraPayload payload = cameraFunctionService.parsePayload(request, camera.getType());

        validatePlate(camera, payload);
        validateDate(payload);

        String plate = payload.getPlate();
        LocalDateTime dateTime = parseDate(payload.getDateTime());
        Optional<VehiclesEntity> vehicleOpt = vehicleService.findActiveVehicleByPlate(plate);

        if (vehicleOpt.isEmpty()) {
            vehicleService.addVehicleOut("OUT", plate, 1, 0, dateTime, camera, 0, 4);
            panelService.print(camera.getDescription(), "KECIN");
            cameraFunctionService.openDoor(camera.getType(), clientIp);
            log.info("CC OUT: unknown vehicle, opened door");
            return CcResult.builder().result("leaved").build();
        }

        VehiclesEntity vehicle = vehicleOpt.get();
        OwnersEntity owner = ownerService.findOwnerByPlate(vehicle.getPlate());

        if (owner != null) {
            log.info("CC OUT: found owner {}", owner.getName());
            vehicleService.updateVehicleAsExit(vehicle, camera, owner.getId());
            panelService.print(camera.getDescription(), "KECIN");
            cameraFunctionService.openDoor(camera.getType(), clientIp);
            return CcResult.builder().result("leaved").build();
        }

        double price = scService.calculateParkingPrice(vehicle);
        if (price == 0.0) {
            vehicleService.updateVehicleAsExit(vehicle, camera, null);
            panelService.print(camera.getDescription(), "KECIN");
            cameraFunctionService.openDoor(camera.getType(), clientIp);
            return CcResult.builder().result("leaved").build();
        }

        panelService.print(camera.getDescription(), "ODENIS GOZLENILIR");
        log.info("CC OUT: not paid, price={}", price);
        throw new NotPaidException();
    }

    private void validatePlate(CamerasEntity camera, CameraPayload payload) {
        String plate = payload.getPlate();
        if (plate == null) {
            panelService.print(camera.getDescription(), "OXUNMADI");
            throw new BadRequestException("Plate number is null");
        }
        if (plate.length() < 7 && !PlateFormatter.inPattern(plate)) {
            panelService.print(camera.getDescription(), "OXUNMADI");
            throw new CameraScanException();
        }
        if (plate.length() == 7) {
            payload.setPlate(PlateFormatter.formatPlate(plate));
        }
        panelService.print(camera.getDescription(), payload.getPlate());
    }

    private void validateDate(CameraPayload payload) {
        if (payload.getDateTime() == null) {
            throw new BadRequestException("Date is null");
        }
    }

    private static LocalDateTime parseDate(String date) {
        return date == null ? null : LocalDateTime.parse(date);
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(CC_DATE_FORMAT);
    }
}
