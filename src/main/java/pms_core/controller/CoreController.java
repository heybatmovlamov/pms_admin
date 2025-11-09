package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.*;
import pms_core.model.response.*;
import pms_core.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CoreController {

    private final CamerasService camerasService;
    private final OrganizationsService organizationsService;
    private final OwnersService ownersService;
    private final ParkingsService parkingsService;
    private final SpacesService spacesService;
    private final TariffsService tariffsService;
    private final UsersService usersService;
    private final VehiclesService vehiclesService;
    /*Cameras*/

    @GetMapping("/cameras")
    public ResponseEntity<List<CameraResponse>> findAllCameras() {
        return ResponseEntity.ok(camerasService.findAll());
    }

    @PostMapping("/camera/add-camera")
    public ResponseEntity<CameraResponse> addCamera(@RequestBody CameraRequest cameraRequest){
        return ResponseEntity.ok(camerasService.addCamera(cameraRequest));
    }

    @GetMapping("/camera/{id}")
    public ResponseEntity<CameraResponse> findCameraById(@PathVariable Integer id){
       return ResponseEntity.ok(camerasService.findById(id));
    }

    @DeleteMapping("/camera/{id}")
    public ResponseEntity<String> deleteCameraById(@PathVariable Integer id){
       return ResponseEntity.ok(camerasService.deleteCamera(id));
    }

    /*Organizations*/

    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationResponse>> findAllOrganizations(){
        return ResponseEntity.ok(organizationsService.findAll());
    }

    @PostMapping("/organization/add-organization")
    public ResponseEntity<OrganizationResponse> addOrganization(@RequestBody OrganizationRequest request){
        return ResponseEntity.ok(organizationsService.addOrganization(request));
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<OrganizationResponse> findOrganizationById(@PathVariable Integer id){
        return ResponseEntity.ok(organizationsService.findById(id));
    }

    @DeleteMapping("/organization/{id}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable Integer id){
        return ResponseEntity.ok(organizationsService.deleteOrganization(id));
    }

    /*Owners*/

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerResponse>> findAllOwners(){
        return ResponseEntity.ok(ownersService.findAll());
    }

    @PostMapping("/owner/add-owners")
    public ResponseEntity<OwnerResponse> addOwner(@RequestBody OwnerRequest request){
        return ResponseEntity.ok(ownersService.addOwner(request));
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<OwnerResponse> findOwnerById(@PathVariable Integer id){
        return ResponseEntity.ok(ownersService.findById(id));
    }

    @DeleteMapping("/owner/{id}")
    public ResponseEntity<String> deleteOwnerById(@PathVariable Integer id){
        return ResponseEntity.ok(ownersService.deleteOwner(id));
    }

    /*Parkings*/

    @GetMapping("/parkings")
    public ResponseEntity<List<ParkingResponse>> findAllParkings(){
        return ResponseEntity.ok(parkingsService.findAll());
    }

    @PostMapping("/parking/add-parking")
    public ResponseEntity<ParkingResponse> addParking(@RequestBody ParkingRequest request){
        return ResponseEntity.ok(parkingsService.addParking(request));
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingResponse> findParkingById(@PathVariable Integer id){
        return ResponseEntity.ok(parkingsService.findById(id));
    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<String> deleteParkingById(@PathVariable Integer id){
        return ResponseEntity.ok(parkingsService.deleteParking(id));
    }

    /*Spaces*/

    @GetMapping("/spaces")
    public ResponseEntity<List<SpaceResponse>> findAllSpaces(){
        return ResponseEntity.ok(spacesService.findAll());
    }

    @PostMapping("/space/add-spaces")
    public ResponseEntity<SpaceResponse> addSpace(@RequestBody SpaceRequest request){
        return ResponseEntity.ok(spacesService.addSpace(request));
    }

    @GetMapping("/space/{id}")
    public ResponseEntity<SpaceResponse> findSpaceById(@PathVariable Integer id){
        return ResponseEntity.ok(spacesService.findById(id));
    }

    @DeleteMapping("/space/{id}")
    public ResponseEntity<String> deleteSpaceById(@PathVariable Integer id){
        return ResponseEntity.ok(spacesService.deleteSpace(id));
    }

    /*Tariff*/

    @GetMapping("/tariffs")
    public ResponseEntity<List<TariffResponse>> findAllTariffs(){
        return ResponseEntity.ok(tariffsService.findAll());
    }

    @PostMapping("/tariff/add-tariff")
    public ResponseEntity<TariffResponse> addTariff(@RequestBody TariffRequest request){
        return ResponseEntity.ok(tariffsService.addTariff(request));
    }

    @GetMapping("/tariff/{id}")
    public ResponseEntity<TariffResponse> findTariffById(@PathVariable Integer id){
        return ResponseEntity.ok(tariffsService.findById(id));
    }

    @DeleteMapping("/tariff/{id}")
    public ResponseEntity<String> deleteTariffById(@PathVariable Integer id){
        return ResponseEntity.ok(tariffsService.deleteTariff(id));
    }

}
