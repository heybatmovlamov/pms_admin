package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.*;
import pms_core.model.response.*;
import pms_core.service.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationsService service;

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> getAllOrganizations(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrganizationResponse> addOrganization(@ModelAttribute  OrganizationRequest request){
        return ResponseEntity.ok(service.addOrganization(request));
    }

    @PutMapping(path = "/edit/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrganizationResponse> editOrganizationById(@PathVariable Integer id ,
                                                                     @ModelAttribute OrganizationRequest request){
        return ResponseEntity.ok(service.updateOrganization(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable Integer id){
        return ResponseEntity.ok(service.deleteOrganization(id));
    }
}
