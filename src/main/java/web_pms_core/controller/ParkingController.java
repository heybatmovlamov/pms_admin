package web_pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_pms_core.model.request.ParkingRequest;
import web_pms_core.model.response.ParkingResponse;
import web_pms_core.service.ParkingsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingsService parkingsService;

    @GetMapping
    public ResponseEntity<List<ParkingResponse>> findAllParkings(){
        return ResponseEntity.ok(parkingsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingResponse> findParkingById(@PathVariable Integer id){
        return ResponseEntity.ok(parkingsService.findById(id));
    }

    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ParkingResponse> addParking(@ModelAttribute ParkingRequest request){
        return ResponseEntity.ok(parkingsService.addParking(request));
    }

    @PutMapping(path="/edit/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ParkingResponse> editParkingById(@PathVariable Integer id,@ModelAttribute ParkingRequest request){
        return ResponseEntity.ok(parkingsService.updateParking(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParkingById(@PathVariable Integer id){
        return ResponseEntity.ok(parkingsService.deleteParking(id));
    }
}
