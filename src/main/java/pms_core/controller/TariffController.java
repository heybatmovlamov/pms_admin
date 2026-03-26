package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.tariff.TariffRequest;
import pms_core.model.response.TariffResponse;
import pms_core.service.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tariffs")
public class TariffController {

    private final TariffsService service;

    @GetMapping
    public ResponseEntity<List<TariffResponse>> findAllTariffs(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffResponse> findTariffById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TariffResponse> addTariff(@RequestBody TariffRequest request){
        return ResponseEntity.ok(service.addTariff(request));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TariffResponse> updateTariffById(@PathVariable Integer id, @RequestBody TariffRequest request){
        return ResponseEntity.ok(service.updateTariff(id,request));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteTariffById(@PathVariable Integer id){
//        return ResponseEntity.ok(service.deleteTariff(id));
//    }
}
