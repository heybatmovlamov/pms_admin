package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.dao.repository.TariffsRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffsRepository tariffRepository;

    public TariffsEntity getTariff(CamerasEntity camera, LocalDateTime time) {
        int day = time.getDayOfWeek().getValue();

        return tariffRepository.findByOrganizationAndParkingAndSpaceAndValidDaysAndActiveAndStatus(
                camera.getOrganization(),
                camera.getParking(),
                camera.getSpace(),
                day
                ,1
                ,1
        ).orElseThrow(() -> new IllegalStateException("Tariff not found"));
    }
}
