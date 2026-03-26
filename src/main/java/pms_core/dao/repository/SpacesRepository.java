package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.SpacesEntity;

import java.util.List;

public interface SpacesRepository extends CrudRepository<SpacesEntity, Integer> {

    @Query("SELECT * FROM spaces")
    List<SpacesEntity> findAllSpaces();
}
