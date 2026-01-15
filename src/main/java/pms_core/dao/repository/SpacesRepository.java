package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.SpacesEntity;

import java.util.List;
import java.util.Optional;

public interface SpacesRepository extends CrudRepository<SpacesEntity, Integer> {

    Optional<SpacesEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);

    @Query("SELECT * FROM pms_core.spaces")
    List<SpacesEntity> findAllSpaces();
}
