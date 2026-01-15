package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.UsersEntity;
import pms_core.dao.repository.UsersRepository;
import pms_core.mapper.UserMapper;
import pms_core.model.request.UserRequest;
import pms_core.model.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public List<UserResponse> findAll(){
        return mapper.toResponse(repository.findAllUsers());
    }

    @Transactional
    public UserResponse addUser(UserRequest request){
        UsersEntity entity = mapper.toEntity(request);
        entity.setStatus(1);
        entity.setCreated(LocalDateTime.now());
        entity.setModified(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public UserResponse updateUser(Integer id , UserRequest request){
        UsersEntity entity = repository.findByIdAndStatus(id,1)
                .orElseThrow(() -> new RuntimeException("User not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setModified(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteUser(Integer id){
        repository.findByIdAndStatus(id, 1)
                .map(user -> {
                    user.setStatus(0);
                    return repository.save(user);})
                .orElseThrow(() -> new RuntimeException("User not found or already inactive"));

        return "User deleted !";
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatus(id, 1)
                .orElseThrow(() -> new RuntimeException("User not found or already inactive")));
    }
}
