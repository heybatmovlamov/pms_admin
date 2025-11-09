package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.ParkingsEntity;

import java.util.Optional;

public interface ParkingsRepository extends JpaRepository<ParkingsEntity, Integer> {

    Optional<ParkingsEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);
}
