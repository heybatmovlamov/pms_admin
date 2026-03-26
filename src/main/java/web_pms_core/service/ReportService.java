package web_pms_core.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import web_pms_core.model.request.ReportDateRangeRequest;
import web_pms_core.model.response.TransactionReportResult;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.TransactionsEntity;
import web_pms_core.dao.repository.transactions.TransactionsRepository;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final TransactionsRepository transactionsRepository;

    @Transactional(readOnly = true)
    public byte[] generateTransactionsExcel(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;

        List<TransactionsEntity> list;
        if (start != null && end != null) {
            list = transactionsRepository.findByDatetimeBetween(start, end, Pageable.unpaged()).getContent();
        } else if (start != null) {
            list = transactionsRepository.findByDatetimeGreaterThanEqual(start, Pageable.unpaged()).getContent();
        } else if (end != null) {
            list = transactionsRepository.findByDatetimeLessThanEqual(end, Pageable.unpaged()).getContent();
        } else {
            list = transactionsRepository.findAll(Pageable.unpaged()).getContent();
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Transactions");

            String[] headers = {"ID", "Organization", "Parking", "Customer", "Vehicle", "Vehicle Plate", "Tariff", "Total Duration", "Amount", "Currency", "Txn ID", "Datetime", "Method", "Detail", "Status"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (TransactionsEntity t : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(t.getId() != null ? t.getId() : 0);
                row.createCell(1).setCellValue(t.getOrganization() != null ? t.getOrganization() : 0);
                row.createCell(2).setCellValue(t.getParking() != null ? t.getParking() : 0);
                row.createCell(3).setCellValue(t.getCustomer() != null ? t.getCustomer() : 0);
                row.createCell(4).setCellValue(t.getVehicle() != null ? t.getVehicle() : 0);
                row.createCell(5).setCellValue(t.getVehiclePlate() != null ? t.getVehiclePlate() : "");
                row.createCell(6).setCellValue(t.getTariff() != null ? t.getTariff() : 0);
                row.createCell(7).setCellValue(t.getTotalDuration() != null ? t.getTotalDuration() : 0);
                row.createCell(8).setCellValue(t.getAmount() != null ? t.getAmount().doubleValue() : 0);
                row.createCell(9).setCellValue(t.getCurrency() != null ? t.getCurrency() : "");
                row.createCell(10).setCellValue(t.getTxnId() != null ? t.getTxnId() : "");
                row.createCell(12).setCellValue(t.getDatetime() != null ? t.getDatetime().format(DATE_TIME_FORMAT) : "");
                row.createCell(13).setCellValue(t.getMethod() != null ? t.getMethod() : "");
                row.createCell(14).setCellValue(t.getDetail() != null ? t.getDetail() : "");
                row.createCell(15).setCellValue(t.getStatus() != null ? t.getStatus() : 0);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Excel generation failed", e);
        }
    }

    public String transactionsExcelFilename(LocalDate startDate, LocalDate endDate) {
        String start = startDate != null ? startDate.toString() : "min";
        String end = endDate != null ? endDate.toString() : "max";
        return "transactions_" + start + "_" + end + ".xlsx";
    }

    public TransactionReportResult generateTransactionsExcel(ReportDateRangeRequest request) {
        LocalDate startDate = request != null ? request.getStartDate() : null;
        LocalDate endDate = request != null ? request.getEndDate() : null;
        byte[] content = generateTransactionsExcel(startDate, endDate);
        String filename = transactionsExcelFilename(startDate, endDate);
        return new TransactionReportResult(content, filename);
    }
}
