package pms_core.dao.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import pms_core.dao.entity.UsersEntity;

import java.util.List;
import java.util.Optional;


public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByUsername(String username);

    @Query("SELECT * FROM users")
    List<UsersEntity> findAllUsers();
}
