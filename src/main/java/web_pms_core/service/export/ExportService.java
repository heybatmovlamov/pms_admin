package web_pms_core.service.export;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_pms_core.dao.entity.TransactionsEntity;
import web_pms_core.dao.entity.VehiclesEntity;
import web_pms_core.dao.repository.transactions.TransactionsRepository;
import web_pms_core.dao.repository.VehiclesRepository;
import web_pms_core.model.request.ReportDateRangeRequest;
import web_pms_core.model.response.TransactionReportResult;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExportService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final VehiclesRepository vehiclesRepository;
    private final TransactionsRepository transactionsRepository;

    @Transactional(readOnly = true)
    public TransactionReportResult generateVehiclesCsv(ReportDateRangeRequest request) {
        LocalDate startDate = request != null ? request.getStartDate() : null;
        LocalDate endDate = request != null ? request.getEndDate() : null;
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;

        List<VehiclesEntity> vehicles = loadVehicles(start, end);
        String csv = buildVehiclesCsv(vehicles);
        byte[] content = csv.getBytes(StandardCharsets.UTF_8);
        String filename = exportFilename("vehicles", startDate, endDate, "csv");
        return new TransactionReportResult(content, filename);
    }

    @Transactional(readOnly = true)
    public TransactionReportResult generateVehiclesExcel(ReportDateRangeRequest request) {
        LocalDate startDate = request != null ? request.getStartDate() : null;
        LocalDate endDate = request != null ? request.getEndDate() : null;
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;
        List<VehiclesEntity> vehicles = loadVehicles(start, end);
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeVehiclesSheet(workbook, vehicles);
            workbook.write(out);
            String filename = exportFilename("vehicles", startDate, endDate, "xlsx");
            return new TransactionReportResult(out.toByteArray(), filename);
        } catch (Exception e) {
            log.error("Vehicles Excel export failed", e);
            throw new RuntimeException("Vehicles Excel export failed", e);
        }
    }

    private List<VehiclesEntity> loadVehicles(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) {
            return vehiclesRepository.findByCreatedBetween(start, end);
        }
        List<VehiclesEntity> all = new ArrayList<>();
        vehiclesRepository.findAll().forEach(all::add);
        return all;
    }

    private String buildVehiclesCsv(List<VehiclesEntity> list) {
        String header = "ID,Plate,Organization,Parking,Space,Tariff,Scanned,Created,Updated,Paid,Exit,Action,Brand,Color,Owner,Type,Description,Active,Status";
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (VehiclesEntity v : list) {
            sb.append(csvInt(v.getId())).append(",")
                    .append(csvStr(v.getPlate())).append(",")
                    .append(csvInt(v.getOrganization())).append(",")
                    .append(csvInt(v.getParking())).append(",")
                    .append(csvInt(v.getSpace())).append(",")
                    .append(csvInt(v.getTariff())).append(",")
                    .append(v.getScanned() != null ? v.getScanned().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(v.getCreated() != null ? v.getCreated().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(v.getUpdated() != null ? v.getUpdated().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(v.getPaid() != null ? v.getPaid().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(v.getExit() != null ? v.getExit().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(csvStr(v.getAction())).append(",")
                    .append(csvStr(v.getBrand())).append(",")
                    .append(csvStr(v.getColor())).append(",")
                    .append(csvInt(v.getOwner())).append(",")
                    .append(csvStr(v.getType())).append(",")
                    .append(csvStr(v.getDescription())).append(",")
                    .append(csvInt(v.getActive())).append(",")
                    .append(csvInt(v.getStatus())).append("\n");
        }
        return sb.toString();
    }

    private void writeVehiclesSheet(Workbook workbook, List<VehiclesEntity> list) {
        Sheet sheet = workbook.createSheet("Vehicles");
        String[] headers = {"ID", "Plate", "Organization", "Parking", "Space", "Tariff", "Scanned", "Created", "Updated", "Paid", "Exit", "Action", "Brand", "Color", "Owner", "Type", "Description", "Active", "Status"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
        int rowNum = 1;
        for (VehiclesEntity v : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(v.getId() != null ? v.getId() : 0);
            row.createCell(1).setCellValue(v.getPlate() != null ? v.getPlate() : "");
            row.createCell(2).setCellValue(v.getOrganization() != null ? v.getOrganization() : 0);
            row.createCell(3).setCellValue(v.getParking() != null ? v.getParking() : 0);
            row.createCell(4).setCellValue(v.getSpace() != null ? v.getSpace() : 0);
            row.createCell(5).setCellValue(v.getTariff() != null ? v.getTariff() : 0);
            row.createCell(6).setCellValue(v.getScanned() != null ? v.getScanned().format(DATE_TIME_FORMAT) : "");
            row.createCell(7).setCellValue(v.getCreated() != null ? v.getCreated().format(DATE_TIME_FORMAT) : "");
            row.createCell(8).setCellValue(v.getUpdated() != null ? v.getUpdated().format(DATE_TIME_FORMAT) : "");
            row.createCell(9).setCellValue(v.getPaid() != null ? v.getPaid().format(DATE_TIME_FORMAT) : "");
            row.createCell(10).setCellValue(v.getExit() != null ? v.getExit().format(DATE_TIME_FORMAT) : "");
            row.createCell(11).setCellValue(v.getAction() != null ? v.getAction() : "");
            row.createCell(12).setCellValue(v.getBrand() != null ? v.getBrand() : "");
            row.createCell(13).setCellValue(v.getColor() != null ? v.getColor() : "");
            row.createCell(14).setCellValue(v.getOwner() != null ? v.getOwner() : 0);
            row.createCell(15).setCellValue(v.getType() != null ? v.getType() : "");
            row.createCell(16).setCellValue(v.getDescription() != null ? v.getDescription() : "");
            row.createCell(17).setCellValue(v.getActive() != null ? v.getActive() : 0);
            row.createCell(18).setCellValue(v.getStatus() != null ? v.getStatus() : 0);
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    @Transactional(readOnly = true)
    public TransactionReportResult generateTransactionsExcel(ReportDateRangeRequest request) {
        LocalDate startDate = request != null ? request.getStartDate() : null;
        LocalDate endDate = request != null ? request.getEndDate() : null;
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;
        List<TransactionsEntity> transactions = loadTransactionsByDateRange(start, end);
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeTransactionsSheet(workbook, transactions);
            workbook.write(out);
            String filename = exportFilename("transactions", startDate, endDate, "xlsx");
            return new TransactionReportResult(out.toByteArray(), filename);
        } catch (Exception e) {
            log.error("Transactions Excel export failed", e);
            throw new RuntimeException("Transactions Excel export failed", e);
        }
    }

    @Transactional(readOnly = true)
    public TransactionReportResult generateTransactionsCsv(ReportDateRangeRequest request) {
        LocalDate startDate = request != null ? request.getStartDate() : null;
        LocalDate endDate = request != null ? request.getEndDate() : null;
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59, 999_999_999) : null;

        List<TransactionsEntity> transactions = loadTransactionsByDateRange(start, end);
        String csv = buildTransactionsCsv(transactions);
        byte[] content = csv.getBytes(StandardCharsets.UTF_8);
        String filename = exportFilename("transactions", startDate, endDate, "csv");
        return new TransactionReportResult(content, filename);
    }


    private List<TransactionsEntity> loadTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) {
            return transactionsRepository.findByDatetimeBetween(start, end, Pageable.unpaged()).getContent();
        }
        if (start != null) {
            return transactionsRepository.findByDatetimeGreaterThanEqual(start, Pageable.unpaged()).getContent();
        }
        if (end != null) {
            return transactionsRepository.findByDatetimeLessThanEqual(end, Pageable.unpaged()).getContent();
        }
        List<TransactionsEntity> all = new ArrayList<>();
        transactionsRepository.findAll().forEach(all::add);
        return all;
    }


    private String buildTransactionsCsv(List<TransactionsEntity> list) {
        String header = "ID,Organization,Parking,Customer,Vehicle,Vehicle Plate,Tariff,Total Duration,Amount,Currency,Txn ID,Datetime,Method,Detail,Status";
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (TransactionsEntity t : list) {
            sb.append(csvInt(t.getId())).append(",")
                    .append(csvInt(t.getOrganization())).append(",")
                    .append(csvInt(t.getParking())).append(",")
                    .append(csvInt(t.getCustomer())).append(",")
                    .append(csvInt(t.getVehicle())).append(",")
                    .append(csvStr(t.getVehiclePlate())).append(",")
                    .append(csvInt(t.getTariff())).append(",")
                    .append(csvInt(t.getTotalDuration())).append(",")
                    .append(t.getAmount() != null ? t.getAmount().doubleValue() : "").append(",")
                    .append(csvStr(t.getCurrency())).append(",")
                    .append(csvStr(t.getTxnId())).append(",")
                    .append(t.getDatetime() != null ? t.getDatetime().format(DATE_TIME_FORMAT) : "").append(",")
                    .append(csvStr(t.getMethod())).append(",")
                    .append(csvStr(t.getDetail())).append(",")
                    .append(csvInt(t.getStatus())).append("\n");
        }
        return sb.toString();
    }

    private void writeTransactionsSheet(Workbook workbook, List<TransactionsEntity> list) {
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
            row.createCell(11).setCellValue(t.getDatetime() != null ? t.getDatetime().format(DATE_TIME_FORMAT) : "");
            row.createCell(12).setCellValue(t.getMethod() != null ? t.getMethod() : "");
            row.createCell(13).setCellValue(t.getDetail() != null ? t.getDetail() : "");
            row.createCell(14).setCellValue(t.getStatus() != null ? t.getStatus() : 0);
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }



    private static String csvStr(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String csvInt(Integer n) {
        return n != null ? String.valueOf(n) : "";
    }

    private static String exportFilename(String prefix, LocalDate startDate, LocalDate endDate, String extension) {
        String start = startDate != null ? startDate.toString() : "min";
        String end = endDate != null ? endDate.toString() : "max";
        return prefix + "_" + start + "_" + end + "." + extension;
    }
}
