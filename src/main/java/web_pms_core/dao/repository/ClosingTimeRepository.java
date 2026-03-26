package web_pms_core.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web_pms_core.dao.entity.ClosingEntity;

import java.util.Optional;

@Repository
public interface ClosingTimeRepository extends CrudRepository<ClosingEntity,Integer> {

    Optional<ClosingEntity> findByDescription(String description);
}
