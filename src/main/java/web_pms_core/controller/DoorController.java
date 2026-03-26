package web_pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web_pms_core.service.local.CameraFunctionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/door")
public class DoorController {

    private final CameraFunctionService service;

    @GetMapping("/open")
    public ResponseEntity<String> open(@RequestParam String ip) {
        return service.sendRequest(ip, Boolean.TRUE);
    }

    @GetMapping("/close")
    public ResponseEntity<String> close(@RequestParam String ip ) {
        return service.sendRequest(ip, Boolean.FALSE);
    }
}
