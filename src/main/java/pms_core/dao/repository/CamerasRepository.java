package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.CamerasEntity;

import java.util.Optional;

public interface CamerasRepository extends JpaRepository<CamerasEntity, Integer> {

    Optional<CamerasEntity> findByIdAndStatusAndActive(Integer id, Integer status , Integer active);
    CamerasEntity findByIp(String ip);
}
