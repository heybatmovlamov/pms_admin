package web_pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import web_pms_core.dao.entity.ParkingsEntity;

import java.util.List;

public interface ParkingsRepository extends CrudRepository<ParkingsEntity, Integer> {


    @Query("SELECT * FROM pms_core.parkings")
    List<ParkingsEntity> findAllParkings();
}
