package pms_core.controller.local;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.local.response.ScResponse;
import pms_core.service.local.SCService;

@RestController
@RequestMapping("/sc/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final SCService scService;

    @GetMapping("/check")
    public ResponseEntity<ScResponse> check(@RequestParam(required = false) String plate) {
        return ResponseEntity.ok(scService.check(plate));
    }

    @GetMapping("/status")
    public ResponseEntity<ScResponse> status(@RequestParam(required = false) String txnId) {
        return ResponseEntity.ok(scService.status(txnId));
    }

    @PostMapping(value = "/pay", consumes = "application/json")
    public ResponseEntity<ScResponse> pay(@RequestBody String requestBody) {
        return ResponseEntity.ok(scService.pay(requestBody));
    }
}
