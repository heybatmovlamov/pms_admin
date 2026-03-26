package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.dao.repository.TariffsRepository;
import pms_core.exception.DataNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffsRepository tariffRepository;

    public TariffsEntity getTariff(CamerasEntity camera, LocalDateTime time) {
        int day = time.getDayOfWeek().getValue();
        log.info("Getting tariff for time {}", day);
        log.info("Camera  {}", camera);
        return tariffRepository.findByOrganizationAndParkingAndSpaceAndValidDaysAndActiveAndStatus(
                camera.getOrganization(),
                camera.getParking(),
                camera.getSpace(),
                day,
                1,
                1).orElseThrow(() -> DataNotFoundException.of("Tariff not found"));
    }

    public TariffsEntity getTariffById(Integer tariffId) {
        return tariffRepository.findById(tariffId)
                .orElseThrow(() -> new IllegalStateException("Tariff not found for id: " + tariffId));
    }

    public TariffsEntity getTariff(Integer organizationId, Integer parkingId, Integer spaceId, int dayOfWeek) {
        return tariffRepository.findByOrganizationAndParkingAndSpaceAndValidDaysAndActiveAndStatus(
                organizationId,
                parkingId,
                spaceId,
                dayOfWeek,
                1,
                1
        ).orElseThrow(() -> new IllegalStateException("No tariff available for this day of the week"));
    }
}
