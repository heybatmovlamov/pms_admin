package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.OwnersEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.model.local.request.CameraPayload;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManageService {

    private final CameraFunctionService cameraFunctionService;
    private final CameraService cameraService;
    private final PanelService panelService;
    private final OwnerService ownerService;
    private final VehicleService vehicleService;
    private final TariffService tariffService;

    public void in(String clientIp, String request) {
        CamerasEntity camera = cameraService.getCamera(clientIp);//ip ə görə kamera tapılması
        CameraPayload cameraPayload = cameraFunctionService.parsePayload(request, camera.getType());// camera type a görə request göndərilməsi

        checkPlate(camera, cameraPayload);
        checkDate(cameraPayload);

        ownerService.ifExistBlockedOwnerThrow(cameraPayload.getPlate());//plate ə görə axtarır əgər varsa block olunmuş throw edir exception

        VehiclesEntity vehicle = vehicleService.getActiveVehicleByPlate(cameraPayload);//plate ə görə aktiv maşını tapır əgər varsa reset edir
        vehicleService.ifVehicleEntered(vehicle);

        TariffsEntity tariff = tariffService.getTariff(camera, parseDate(cameraPayload.getDateTime()));

        VehiclesEntity in = vehicleService.addVehicleIn("IN", cameraPayload.getPlate(), parseDate(cameraPayload.getDateTime()), vehicle.getAction(), tariff.getId(), tariff.getDurationStart(), camera);

        panelService.print(camera.getDescription(), "KECIN");
        cameraFunctionService.openDoor(camera.getType(), clientIp);//camera typeə və ip yə görə şlanqbaunu açır

        log.info("successfully opened door for {}", in);
    }

    public void out(String clientIp, String request) {
        CamerasEntity camera = cameraService.getCamera(clientIp);//ip ə görə kamera tapılması
        CameraPayload cameraPayload = cameraFunctionService.parsePayload(request, camera.getType());// camera type a görə request göndərilməsi

        checkPlate(camera, cameraPayload);
        checkDate(cameraPayload);

        VehiclesEntity vehicle = vehicleService.getActiveVehicleByPlate(cameraPayload);//plate ə görə aktiv maşını tapır əgər varsa reset edir
        if (vehicle == null) {
            panelService.print(camera.getDescription(), "KECIN");
            cameraFunctionService.openDoor(camera.getType(), clientIp);//camera typeə və ip yə görə şlanqbaunu açır

            VehiclesEntity vehiclesEntity = vehicleService.addVehicleOut("OUT",
                    cameraPayload.getPlate(),
                    1,
                    0,
                    parseDate(cameraPayload.getDateTime()),
                    camera,
                    0,
                    4);

            log.info("successfully opened vehicle out for {}", vehiclesEntity);
        }

        OwnersEntity owner = ownerService.findOwnerByPlate(cameraPayload.getPlate());
        if (owner != null) {
            log.info("Found Owner: " + owner.getName());
            vehicleService.addVehicleOut("OUT",
                    cameraPayload.getPlate(),
                    1,
                    owner.getId(),
                    parseDate(cameraPayload.getDateTime()),
                    camera,
                    0,
                    3);

            panelService.print(camera.getDescription(), "KECIN");
            cameraFunctionService.openDoor(camera.getType(), clientIp);//camera typeə və ip yə görə şlanqbaunu açır
        }





    }

    private LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date);
    }

    private void checkPlate(CamerasEntity camera, CameraPayload cameraPayload) {
        String plate = cameraPayload.getPlate();
        if (plate == null) {
            String response = "Plate number is null";
            log.info("{}", response);

            panelService.print(camera.getDescription(), "OXUNMADI");
            throw new RuntimeException(response);
        }

        if (plate.length() < 7 && !PlateFormatter.inPattern(plate)) {
            String response = "CAMERA SCAN ERROR (LENGTH OR PATTERN)";
            log.info("{}", response);

            panelService.print(camera.getDescription(), "OXUNMADI");

            throw new RuntimeException(response);
        }

        if (plate.length() == 7) {
            plate = PlateFormatter.formatPlate(plate);
        }
        panelService.print(camera.getDescription(), plate);
    }

    private void checkDate(CameraPayload cameraPayload) {
        if (cameraPayload.getDateTime() == null) {
            String response = "Date is null";
            log.info("{}", response);
            throw new RuntimeException(response);
        }
    }
}
