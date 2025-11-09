package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.ParkingsRepository;
import pms_core.mapper.ParkingMapper;
import pms_core.model.request.ParkingRequest;
import pms_core.model.response.ParkingResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingsService {

    private final ParkingsRepository repository;
    private final ParkingMapper mapper;

    @Transactional(readOnly = true)
    public List<ParkingResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public ParkingResponse addParking(ParkingRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public ParkingResponse updateParking(ParkingRequest request){
//
//    }

    @Transactional
    public String deleteParking(Integer id){
        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(parking -> {
                    parking.setActive(0);
                    return repository.save(parking);})
                .orElseThrow(() -> new RuntimeException("Parking not found or already inactive"));

        return "Parking deleted !";
    }

    @Transactional(readOnly = true)
    public ParkingResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Parking not found or already inactive")));
    }
}
