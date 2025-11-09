package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.TariffsEntity;

import java.util.Optional;

public interface TariffsRepository extends JpaRepository<TariffsEntity, Integer> {

    Optional<TariffsEntity> findByIdAndStatusAndActive(Integer id, Integer status, Integer active);
}
