package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.CustomersEntity;

import java.util.List;
import java.util.Optional;

public interface CustomersRepository extends CrudRepository<CustomersEntity, Integer> {


    @Query("SELECT * FROM pms_core.customers WHERE active = 1 ORDER BY id DESC")
    List<CustomersEntity> findAllActive();
}
