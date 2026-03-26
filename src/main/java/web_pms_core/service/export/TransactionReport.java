package web_pms_core.service.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web_pms_core.model.request.ReportDateRangeRequest;
import web_pms_core.model.response.TransactionReportResult;

@Service
@RequiredArgsConstructor
public class TransactionReport {

    private final ExportService exportService;

    public TransactionReportResult generate(ReportDateRangeRequest request) {
        String type = request != null && request.getType() != null
                ? request.getType().trim().toLowerCase()
                : "excel";
        if ("csv".equals(type)) {
            return exportService.generateTransactionsCsv(request);
        }
        return exportService.generateTransactionsExcel(request);
    }
}
