package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.CameraRequest;
import pms_core.model.response.CameraResponse;
import pms_core.service.CamerasService;

import java.util.List;

@RestController
@RequestMapping("/cameras")
@RequiredArgsConstructor
public class CamerasController {

    private final CamerasService service;

    @GetMapping
    public ResponseEntity<List<CameraResponse>> getAllCameras() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CameraResponse> getCameraById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CameraResponse> addCamera(@RequestBody CameraRequest cameraRequest){
        return ResponseEntity.ok(service.addCamera(cameraRequest));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CameraResponse> editCameraById(@PathVariable Integer id, @RequestBody CameraRequest cameraRequest){
        return ResponseEntity.ok(service.updateCamera(id, cameraRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCameraById(@PathVariable Integer id){
        return ResponseEntity.ok(service.deleteCamera(id));
    }
}
