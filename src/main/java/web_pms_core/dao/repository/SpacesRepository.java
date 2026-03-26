package web_pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import web_pms_core.dao.entity.SpacesEntity;

import java.util.List;

public interface SpacesRepository extends CrudRepository<SpacesEntity, Integer> {


    @Query("SELECT * FROM pms_core.spaces")
    List<SpacesEntity> findAllSpaces();
}
