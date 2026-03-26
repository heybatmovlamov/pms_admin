package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.ReportDateRangeRequest;
import pms_core.model.response.TransactionReportResult;
import pms_core.service.export.TransactionReport;
import pms_core.service.export.VehicleReport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final TransactionReport transactionReport;
    private final VehicleReport vehicleReport;

//    public ResponseEntity<byte[]> endDay(){
//
//    }

    @PostMapping("/transactions")
    public ResponseEntity<byte[]> downloadTransactionReport(@RequestBody(required = false) ReportDateRangeRequest request) {
        TransactionReportResult result = transactionReport.generate(request);
        return ResponseEntity.ok()
                .headers(buildHeaders(result.getFilename()))
                .body(result.getContent());
    }

    @PostMapping("/vehicles")
    public ResponseEntity<byte[]> downloadVehicleReport(@RequestBody(required = false) ReportDateRangeRequest request) {
        TransactionReportResult result = vehicleReport.generate(request);
        return ResponseEntity.ok()
                .headers(buildHeaders(result.getFilename()))
                .body(result.getContent());
    }

    private HttpHeaders buildHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        if (filename != null && filename.toLowerCase().endsWith(".csv")) {
            headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        } else {
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        }
        return headers;
    }
}
