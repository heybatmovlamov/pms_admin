package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.repository.ParkingsRepository;
import pms_core.mapper.ParkingMapper;
import pms_core.model.request.ParkingRequest;
import pms_core.model.response.ParkingResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingsService {

    private final ParkingsRepository repository;
    private final ParkingMapper mapper;

    @Transactional(readOnly = true)
    public List<ParkingResponse> findAll(){
        return mapper.toResponse(repository.findAllParkings());
    }

    @Transactional
    public ParkingResponse addParking(ParkingRequest request){
        ParkingsEntity entity = mapper.toEntity(request);
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    public ParkingResponse updateParking(Integer id,ParkingRequest request){
        ParkingsEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setUpdated(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteParking(Integer id){
        repository.findById(id)
                .map(parking -> {
                    parking.setActive(0);
                    return repository.save(parking);})
                .orElseThrow(() -> new RuntimeException("Parking not found or already inactive"));

        return "Parking deleted !";
    }

    @Transactional(readOnly = true)
    public ParkingResponse findById(Integer id){
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking not found or already inactive")));
    }
}
