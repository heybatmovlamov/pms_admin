package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.VehiclesEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehiclesRepository extends CrudRepository<VehiclesEntity, Integer> {

    Optional<VehiclesEntity> findByPlate(String plate);
    Optional<VehiclesEntity> findFirstByPlateAndActiveAndStatusOrderByCreatedDesc(String plate, Integer active , Integer status);

    @Query("SELECT * FROM vehicles")
    List<VehiclesEntity> findAllVehicles();

    List<VehiclesEntity> findByCreatedBetween(LocalDateTime start, LocalDateTime end);
}
