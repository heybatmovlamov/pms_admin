package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.TariffsEntity;

import java.util.List;
import java.util.Optional;

public interface  TariffsRepository extends CrudRepository<TariffsEntity, Integer> {

    Optional<TariffsEntity> findByOrganizationAndParkingAndSpaceAndValidDaysAndActiveAndStatus(Integer organization, Integer parking, Integer space, Integer validDays, Integer active, Integer status);

    @Query("SELECT * FROM tariffs")
    List<TariffsEntity> findAllTariffs();
}
