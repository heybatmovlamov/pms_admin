package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.SpaceRequest;
import pms_core.model.response.SpaceResponse;
import pms_core.service.SpacesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    private final SpacesService spacesService;

    @GetMapping
    public ResponseEntity<List<SpaceResponse>> findAllSpaces(){
        return ResponseEntity.ok(spacesService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> findSpaceById(@PathVariable Integer id){
        return ResponseEntity.ok(spacesService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<SpaceResponse> addSpace(@RequestBody SpaceRequest request){
        return ResponseEntity.ok(spacesService.addSpace(request));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<SpaceResponse> editSpace(@PathVariable Integer id ,
                                                   @RequestBody SpaceRequest request){
        return ResponseEntity.ok(spacesService.updateSpace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpaceById(@PathVariable Integer id){
        return ResponseEntity.ok(spacesService.deleteSpace(id));
    }

    //updateSpace
}
