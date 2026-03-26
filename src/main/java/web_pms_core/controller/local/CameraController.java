package web_pms_core.controller.local;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_pms_core.model.local.response.CcResult;
import web_pms_core.service.local.ManageService;

@RestController
@RequestMapping("/cc/v1")
@RequiredArgsConstructor
public class CameraController {

    private final ManageService manageService;

    @GetMapping("/info")
    public ResponseEntity<CcResult> info() {
        return ResponseEntity.ok(manageService.info());
    }

    @PostMapping("/in")
    public ResponseEntity<CcResult> vehicleIn(
            @RequestBody String request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(manageService.in(httpRequest.getRemoteAddr(), request));
    }

    @PostMapping("/out")
    public ResponseEntity<CcResult> vehicleOut(
            @RequestBody String request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(manageService.out(httpRequest.getRemoteAddr(), request));
    }
}
