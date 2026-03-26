package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.UsersEntity;
import web_pms_core.dao.repository.UsersRepository;
import web_pms_core.mapper.UserMapper;
import web_pms_core.model.request.UserRequest;
import web_pms_core.model.response.UserResponse;

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
        UsersEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        mapper.updateEntityFromRequest(request, entity);
        entity.setModified(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public String deleteUser(Integer id){
        repository.findById(id)
                .map(user -> {
                    user.setStatus(0);
                    return repository.save(user);})
                .orElseThrow(() -> new RuntimeException("User not found or already inactive"));

        return "User "+ id + " deleted ! ";
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Integer id){
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found or already inactive")));
    }

    public UserResponse findByUsername(String username){
        return mapper.toResponse(repository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found or already inactive")));
    }
}
