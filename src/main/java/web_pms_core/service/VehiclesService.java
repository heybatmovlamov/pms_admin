package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.VehiclesEntity;
import web_pms_core.dao.repository.VehiclesRepository;
import web_pms_core.exception.DataNotFoundException;
import web_pms_core.mapper.VehicleMapper;
import web_pms_core.model.request.vehicle.VehicleRequest;
import web_pms_core.model.request.vehicle.VehicleUpdateRequest;
import web_pms_core.model.response.VehicleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesService {

    private final VehiclesRepository repository;
    private final VehicleMapper mapper;

    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll() {
        return mapper.toResponse(repository.findAllVehicles());
    }

    @Transactional
    public VehicleResponse addVehicle(VehicleRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional
    public VehicleResponse updateVehicle(Integer id,VehicleUpdateRequest request) {
        VehiclesEntity vehiclesEntity = repository.findById(id).orElseThrow();
        vehiclesEntity.setPlate(request.getPlate());
        vehiclesEntity.setBrand(request.getBrand());
        return mapper.toResponse(repository.save(vehiclesEntity));
    }

    @Transactional
    public String deleteVehicle(Integer id) {
        repository.findById(id)
                .map(vehicle -> {
                    vehicle.setActive(0);
                    return repository.save(vehicle);
                })
                .orElseThrow(() -> DataNotFoundException.of("Vehicle not found or already inactive"));
        return "Vehicle " + id + " deleted !";
    }

    @Transactional(readOnly = true)
    public VehicleResponse findVehicleByPlate(String plate) {
        return mapper.toResponse(repository.findByPlate(plate)
                .orElseThrow(() -> DataNotFoundException.of("Vehicle not found or already inactive")));
    }
}
