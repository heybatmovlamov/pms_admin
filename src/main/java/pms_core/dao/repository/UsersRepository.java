package pms_core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pms_core.dao.entity.UsersEntity;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByIdAndStatus(Integer id, Integer status);
}
