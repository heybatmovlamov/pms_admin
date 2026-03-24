package pms_core.service.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pms_core.model.request.ReportDateRangeRequest;
import pms_core.model.response.TransactionReportResult;

@Service
@RequiredArgsConstructor
public class VehicleReport {

    private final ExportService exportService;

    public TransactionReportResult generate(ReportDateRangeRequest request) {
        String type = request != null && request.getType() != null
                ? request.getType().trim().toLowerCase()
                : "excel";
        if ("csv".equals(type)) {
            return exportService.generateVehiclesCsv(request);
        }
        return exportService.generateVehiclesExcel(request);
    }
}
