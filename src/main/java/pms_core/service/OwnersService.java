package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.OwnersRepository;
import pms_core.mapper.OwnerMapper;
import pms_core.model.request.OwnerRequest;
import pms_core.model.response.OwnerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnersService {

    private final OwnersRepository repository;
    private final OwnerMapper mapper;

    @Transactional(readOnly = true)
    public List<OwnerResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public OwnerResponse addOwner(OwnerRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public OwnerResponse updateOwner(OwnerRequest request){
//
//    }

    @Transactional
    public String deleteOwner(Integer id){

        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(owner -> {
                    owner.setActive(0);
                    return repository.save(owner);})
                .orElseThrow(() -> new RuntimeException("Owner not found or already inactive"));

        return "Owner deleted !";
    }

    @Transactional(readOnly = true)
    public OwnerResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Owner not found or already inactive")));
    }
}
