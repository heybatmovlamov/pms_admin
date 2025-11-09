package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.VehiclesEntity;

import java.util.Optional;

public interface VehiclesRepository extends JpaRepository<VehiclesEntity, Integer> {

    Optional<VehiclesEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);
}
