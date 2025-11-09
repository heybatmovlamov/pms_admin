package pms_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.repository.TariffsRepository;
import pms_core.mapper.TariffMapper;
import pms_core.model.request.TariffRequest;
import pms_core.model.response.TariffResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffsService {

    private final TariffsRepository repository;
    private final TariffMapper mapper;

    @Transactional(readOnly = true)
    public List<TariffResponse> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public TariffResponse addTariff(TariffRequest request){
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

//    public TariffResponse updateTariff(TariffRequest request){
//
//    }

    @Transactional
    public String deleteTariff(Integer id){
        repository.findByIdAndStatusAndActive(id, 1, 1)
                .map(tariff -> {
                    tariff.setActive(0);
                    return repository.save(tariff);})
                .orElseThrow(() -> new RuntimeException("Tariff not found or already inactive"));

        return "Tariff deleted !";
    }

    @Transactional(readOnly = true)
    public TariffResponse findById(Integer id){
        return mapper.toResponse(repository.findByIdAndStatusAndActive(id, 1, 1)
                .orElseThrow(() -> new RuntimeException("Tariff not found or already inactive")));
    }
}
