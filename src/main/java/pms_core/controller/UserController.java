package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.UserRequest;
import pms_core.model.response.UserResponse;
import pms_core.service.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UsersService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> create(@ModelAttribute UserRequest userRequest){
        return ResponseEntity.ok(service.addUser(userRequest));
    }

    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> update(@PathVariable Integer id,
                                               @ModelAttribute UserRequest userRequest){
        return ResponseEntity.ok(service.updateUser(id, userRequest));
    }

    @GetMapping("/export")
    public ResponseEntity<UserResponse> export(){
        return ResponseEntity.ok(null);
    }
}
