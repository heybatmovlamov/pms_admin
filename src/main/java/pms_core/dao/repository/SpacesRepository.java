package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.SpacesEntity;

import java.util.Optional;

public interface SpacesRepository extends JpaRepository<SpacesEntity, Integer> {

    Optional<SpacesEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);
}
