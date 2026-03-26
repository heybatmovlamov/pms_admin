package web_pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web_pms_core.dao.entity.OwnersEntity;
import web_pms_core.dao.repository.OwnersRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnersRepository repository;

    public void ifExistBlockedOwnerThrow(String plate) {
        if (isBlocked(plate)) {
            log.info("Owner with plate ({}) is Blocked..", plate);
            throw new RuntimeException("BLOCKED");
        }
    }

    /** Returns true if plate belongs to a blocked owner (active=1, status=3). */
    public boolean isBlocked(String plate) {
        return repository.findByPlateAndActiveAndStatus(plate, 1, 3).isPresent();
    }

    /** Returns owner by plate (active, not blocked); null if not found. */
    public OwnersEntity findOwnerByPlate(String plate) {
        return repository.findByPlateAndActiveAndStatus(plate, 1, 1).orElse(null);
    }
}
