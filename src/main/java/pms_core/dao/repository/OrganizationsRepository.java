package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.model.response.OrganizationResponse;

import java.util.List;
import java.util.Optional;

public interface OrganizationsRepository extends CrudRepository<OrganizationsEntity, Integer> {

    @Query("SELECT * FROM pms_core.organizations")
    List<OrganizationsEntity> findAllOrganizations();

}
