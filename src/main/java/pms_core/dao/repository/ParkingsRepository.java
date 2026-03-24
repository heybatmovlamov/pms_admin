package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.model.response.CameraResponse;

import java.util.List;
import java.util.Optional;

public interface ParkingsRepository extends CrudRepository<ParkingsEntity, Integer> {


    @Query("SELECT * FROM pms_core.parkings")
    List<ParkingsEntity> findAllParkings();
}
