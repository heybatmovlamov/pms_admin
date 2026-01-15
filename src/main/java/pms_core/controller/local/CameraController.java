package pms_core.controller.local;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pms_core.service.local.ManageService;

@RestController
@RequestMapping("/cc")
@RequiredArgsConstructor
@Slf4j
public class CameraController {

    private final ManageService manageService;

    @PostMapping("/in")
    public void vehicleIn(
            @RequestBody String request,
            HttpServletRequest httpRequest
    ) {
        manageService.in(httpRequest.getRemoteAddr(),request);
    }

//    @PostMapping("/out")
//    public ResponseEntity<?> vehicleOut(
//            @RequestBody CameraPayload request,
//            HttpServletRequest httpRequest) {
//        String clientIp = httpRequest.getRemoteAddr();
//        return ResponseEntity.ok(
//                cameraService.vehicleOut(request, clientIp)
//        );
//    }
}

