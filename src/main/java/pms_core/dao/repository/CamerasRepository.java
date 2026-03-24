package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.CamerasEntity;

import java.util.List;
import java.util.Optional;

public interface CamerasRepository extends CrudRepository<CamerasEntity, Integer> {

    Optional<CamerasEntity> findByIp(String ip);

    @Query("SELECT * FROM pms_core.cameras")
    List<CamerasEntity> findAllCamera();
}
