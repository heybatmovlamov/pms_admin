package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pms_core.dao.entity.OwnersEntity;
import pms_core.dao.repository.OwnersRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnersRepository repository;

    public void ifExistBlockedOwnerThrow(String plate){
        repository.findByPlateAndActiveAndStatus(plate,1,3).ifPresent(v -> {
                log.info("Owner with plate (" + plate + ") is Blocked..");
                throw new RuntimeException("BLOCKED");});
    }

    public OwnersEntity findOwnerByPlate(String plate){
        return repository.findByPlateAndActiveAndStatus(plate,1,1).get();
    }
}
