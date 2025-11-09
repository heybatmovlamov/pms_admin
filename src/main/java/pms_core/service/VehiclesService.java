package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.VehiclesRepository;
import pms_core.mapper.VehicleMapper;
import pms_core.model.request.VehicleRequest;
import pms_core.model.response.VehicleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesService {

    private final VehiclesRepository repository;
    private final VehicleMapper mapper;

    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public VehicleResponse addVehicle(VehicleRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public VehicleResponse updateVehicle(VehicleRequest request){
//
//    }

    @Transactional
    public String deleteVehicle(Integer id){
        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(vehicle -> {
                    vehicle.setActive(0);
                    return repository.save(vehicle);})
                .orElseThrow(() -> new RuntimeException("Vehicle not found or already inactive"));

        return "Vehicle deleted !";
    }

    @Transactional(readOnly = true)
    public VehicleResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Vehicle not found or already inactive")));
    }
}
