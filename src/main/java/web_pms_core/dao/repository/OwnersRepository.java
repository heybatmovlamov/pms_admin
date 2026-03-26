package web_pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import web_pms_core.dao.entity.OwnersEntity;

import java.util.List;
import java.util.Optional;

public interface OwnersRepository extends CrudRepository<OwnersEntity, Integer> {

    Optional<OwnersEntity> findByPlateAndActiveAndStatus(String plate,Integer active, Integer status);

    @Query("SELECT * FROM owners")
    List<OwnersEntity> findAllOwners();
}
