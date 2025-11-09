package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.OwnersEntity;

import java.util.Optional;

public interface OwnersRepository extends JpaRepository<OwnersEntity, Integer> {

    Optional<OwnersEntity> findByIdAndStatusAndActive(Integer id, Integer status , Integer active);
}
