package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.OrganizationsEntity;

import java.util.Optional;

public interface OrganizationsRepository extends JpaRepository<OrganizationsEntity, Integer> {

    Optional<OrganizationsEntity> findByIdAndStatus(Integer id, Integer status);
}
