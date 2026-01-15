package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.vehicle.VehicleUpdateRequest;
import pms_core.model.response.VehicleResponse;
import pms_core.service.VehiclesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehiclesService vehiclesService;


    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(){
        //pagination add in here
        return ResponseEntity.ok(vehiclesService.findAll());
    }

    @GetMapping("/plate")
    public ResponseEntity<VehicleResponse> getVehicleByPlate(@RequestParam String plate){
        return ResponseEntity.ok(vehiclesService.findVehicleByPlate(plate));
    }

    @PutMapping
    public ResponseEntity<VehicleResponse> updateVehicleByPlate(@RequestBody VehicleUpdateRequest request){
        return ResponseEntity.ok(vehiclesService.updateVehicle(request));
    }

    //export vehicle data
    @GetMapping("/export")
    public ResponseEntity<VehicleResponse> exportVehicleByPlate(@RequestParam String plate){
        return ResponseEntity.ok(null);
    }

}
