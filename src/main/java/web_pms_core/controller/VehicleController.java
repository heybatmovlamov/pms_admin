package web_pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_pms_core.model.request.vehicle.VehicleUpdateRequest;
import web_pms_core.model.response.VehicleResponse;
import web_pms_core.service.VehiclesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehiclesService vehiclesService;


    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(){
        //filter gunluk ayliq ne zaman girib
        //subscribe idendifikasiyasi
        //vehicle owner nut null return
        return ResponseEntity.ok(vehiclesService.findAll());
    }

    @GetMapping("/plate")
    public ResponseEntity<VehicleResponse> getVehicleByPlate(@RequestParam String plate){
        return ResponseEntity.ok(vehiclesService.findVehicleByPlate(plate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicleByPlate(@PathVariable Integer id,@RequestBody VehicleUpdateRequest request){
        return ResponseEntity.ok(vehiclesService.updateVehicle(id,request));
    }
}
