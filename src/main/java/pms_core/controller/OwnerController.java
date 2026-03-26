package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.OwnerRequest;
import pms_core.model.response.OwnerResponse;
import pms_core.service.OwnersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private final OwnersService service;

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> findAllOwners(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> findOwnerById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OwnerResponse> addOwner(@RequestBody OwnerRequest request){
        return ResponseEntity.ok(service.addOwner(request));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Integer id,
                                                     @RequestBody OwnerRequest request){
        return ResponseEntity.ok(service.updateOwner(id, request));
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<byte[]> exportOwner(@PathVariable Integer id){
        return ResponseEntity.ok(null);
    }
}
