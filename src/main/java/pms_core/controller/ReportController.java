package pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pms_core.model.request.ReportDateRangeRequest;
import pms_core.service.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/transactions/excel")
    public ResponseEntity<byte[]> downloadTransactionsExcel(@RequestBody(required = false) ReportDateRangeRequest request) {
        var result = reportService.generateTransactionsExcel(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", result.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .body(result.getContent());
    }
}
