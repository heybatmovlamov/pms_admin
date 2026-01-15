package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.VehiclesEntity;

import java.util.List;
import java.util.Optional;

public interface VehiclesRepository extends CrudRepository<VehiclesEntity, Integer> {

    Optional<VehiclesEntity> findByPlateAndStatusAndActive(String plate, Integer status, Integer active);
    Optional<VehiclesEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);
    Optional<VehiclesEntity> findFirstByPlateAndActiveOrderByCreatedDesc(String plate, Integer active);

    @Query("SELECT * FROM vehicles")
    List<VehiclesEntity> findAllVehicles();
}
