package web_pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import web_pms_core.dao.entity.OrganizationsEntity;

import java.util.List;

public interface OrganizationsRepository extends CrudRepository<OrganizationsEntity, Integer> {

    @Query("SELECT * FROM pms_core.organizations")
    List<OrganizationsEntity> findAllOrganizations();

}
