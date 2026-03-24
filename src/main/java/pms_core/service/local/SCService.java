package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pms_core.dao.entity.TariffsEntity;
import pms_core.dao.entity.TransactionsEntity;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.dao.repository.transactions.TransactionsRepository;
import pms_core.exception.BadRequestException;
import pms_core.exception.DataNotFoundException;
import pms_core.model.local.request.ScPayRequest;
import pms_core.model.local.response.ScResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SCService {

    private static final DateTimeFormatter SC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String TEST_PLATE = "77CC502";
    private static final int STATUS_PAID = 1;

    private final VehicleService vehicleService;
    private final TariffService tariffService;
    private final TransactionsRepository transactionsRepository;

    private int testFlag = 1;

    @Transactional(readOnly = true)
    public ScResponse check(String plate) {
        if (plate == null || plate.isBlank()) {
            throw new BadRequestException("Bad Request");
        }

        if (TEST_PLATE.equalsIgnoreCase(plate)) {
            double amount = testFlag == 1 ? 0.01 : 0.00;
            testFlag = 1;
            return ScResponse.builder()
                    .status("OK")
                    .message("Success")
                    .plate(TEST_PLATE)
                    .amount(amount)
                    .currency("AZN")
                    .entered("2025-01-01 11:51:11")
                    .leaveTill("2025-01-01 11:51:11")
                    .build();
        }

        Optional<VehiclesEntity> opt = vehicleService.findActiveVehicleByPlate(plate);
        if (opt.isEmpty()) {
            throw new DataNotFoundException("Not Found");
        }

        VehiclesEntity vehicle = opt.get();
        double price = calculateParkingPrice(vehicle);
        String entered = format(vehicle.getScanned());
        String leaveTill = format(vehicle.getPaid());

        return ScResponse.builder()
                .status("OK")
                .message("Success")
                .plate(vehicle.getPlate())
                .amount(price)
                .currency("AZN")
                .entered(entered)
                .leaveTill(leaveTill)
                .build();
    }

    public ScResponse status(String txnId) {
        if (txnId == null || txnId.isBlank()) {
            throw new BadRequestException("Bad Request");
        }
        return ScResponse.builder()
                .status("READY")
                .code("")
                .build();
    }

    @Transactional
    public ScResponse pay(String requestBody) {
        ScPayRequest request = parsePayRequest(requestBody);
        if (request == null || request.getPlate() == null || request.getTxnId() == null || request.getAmount() == null) {
            throw new BadRequestException("Bad Request");
        }

        String plate = request.getPlate();
        String txnId = request.getTxnId();
        double amount = request.getAmount();
        String currency = request.getCurrency() != null ? request.getCurrency() : "AZN";
        String method = request.getMethod() != null ? request.getMethod() : "CASH";

        if (TEST_PLATE.equalsIgnoreCase(plate)) {
            testFlag = 0;
            return ScResponse.builder()
                    .status("OK")
                    .message("Success")
                    .plate(TEST_PLATE)
                    .amount(0.01)
                    .currency("AZN")
                    .entered("2025-01-01 11:51:11")
                    .leaveTill("2025-01-01 11:51:11")
                    .build();
        }

        Optional<TransactionsEntity> existingTxn = transactionsRepository.findFirstByTxnIdAndStatus(txnId, STATUS_PAID);
        if (existingTxn.isPresent()) {
            return ScResponse.builder()
                    .status("Duplicate payment")
                    .message("Payment with transactionId " + txnId + " already exsit")
                    .build();
        }

        Optional<VehiclesEntity> optVehicle = vehicleService.findActiveVehicleByPlate(plate);
        if (optVehicle.isEmpty()) {
            throw new DataNotFoundException("Not Found");
        }

        VehiclesEntity vehicle = optVehicle.get();
        long totalMinutes = java.time.Duration.between(vehicle.getCreated(), LocalDateTime.now()).toMinutes();

        TransactionsEntity transaction = TransactionsEntity.builder()
                .organization(vehicle.getOrganization())
                .parking(vehicle.getParking())
                .customer(null)
                .vehicle(vehicle.getId())
                .vehiclePlate(vehicle.getPlate())
                .tariff(vehicle.getTariff())
                .txnId(txnId)
                .totalDuration((int) totalMinutes)
                .amount(BigDecimal.valueOf(amount))
                .currency(currency)
                .reference(null)
                .datetime(LocalDateTime.now())
                .method(method != null ? method : "Cash")
                .description(null)
                .status(STATUS_PAID)
                .build();
        transactionsRepository.save(transaction);

        TariffsEntity tariff = tariffService.getTariffById(vehicle.getTariff());
        double currentDebt = calculateParkingPrice(vehicle);
        double remainingDebt = Math.max(currentDebt - amount, 0.0);

        if (amount + 0.0001 >= currentDebt) {
            vehicle.setPaid(LocalDateTime.now().plusMinutes(tariff.getDurationLeave()));
            vehicle.setStatus(2);
            vehicle.setDescription("Paid");
            vehicleService.updateVehicle(vehicle);

            return ScResponse.builder()
                    .status("OK")
                    .message("Success")
                    .plate(vehicle.getPlate())
                    .amount(amount)
                    .currency("AZN")
                    .entered(format(vehicle.getScanned()))
                    .leaveTill(format(vehicle.getPaid()))
                    .build();
        } else {
            vehicle.setStatus(2);
            vehicle.setDescription("Partial Paid");
            vehicleService.updateVehicle(vehicle);

            return ScResponse.builder()
                    .status("PARTIAL")
                    .message("Amount is less than current debt")
                    .plate(vehicle.getPlate())
                    .amount(amount)
                    .remaining(remainingDebt)
                    .currency("AZN")
                    .entered(format(vehicle.getScanned()))
                    .leaveTill(format(vehicle.getPaid()))
                    .build();
        }
    }

    public double calculateParkingPrice(VehiclesEntity vehicle) {
        try {
            LocalDateTime now = LocalDateTime.now();
            TariffsEntity tariff = tariffService.getTariff(
                    vehicle.getOrganization(),
                    vehicle.getParking(),
                    vehicle.getSpace(),
                    now.getDayOfWeek().getValue()
            );

            LocalDateTime paidTill = vehicle.getPaid();
            if (paidTill != null && paidTill.isAfter(now)) {
                return 0.0;
            }

            double totalMinutes = java.time.Duration.between(vehicle.getScanned(), now).toMinutes();
            log.debug("Total minutes: {}, free minutes: {}", totalMinutes, tariff.getDurationStart());

            double totalAmount;

            if (totalMinutes < tariff.getDurationStart()) {
                return 0.0;
            }

            double chargeableMinutes = totalMinutes - tariff.getDurationStart();
            double rate = tariff.getRate().doubleValue();
            double min = tariff.getMin().doubleValue();
            double max = tariff.getMax().doubleValue();
            double discount = tariff.getDiscount().doubleValue();
            double extraCharge = tariff.getExtraCharge().doubleValue();

            if (totalMinutes >= 5 * 60 && totalMinutes <= 10 * 60) {
                totalAmount = 10.0;
                log.debug("Applied flat fee (5–10h): {}", totalAmount);
            } else if (totalMinutes > 10 * 60) {
                totalAmount = 30.0;
                log.debug("Applied flat fee (>10h): {}", totalAmount);
            } else {
                double steps = Math.ceil(chargeableMinutes / (double) tariff.getDurationTime());
                totalAmount = steps * rate;
                if (totalAmount < min) totalAmount = min;
                if (totalAmount > max) totalAmount = max;
                if (discount > 0) totalAmount -= (totalAmount * discount / 100.0);
                if (extraCharge > 0) totalAmount += extraCharge;
            }

            if (vehicle.getStatus() == 2) {
                BigDecimal paidSum = transactionsRepository.sumAmountByVehicleIdAndStatus(vehicle.getId());
                if (paidSum != null) {
                    double paidAmount = paidSum.doubleValue();
                    totalAmount = totalAmount - paidAmount;
                    if (totalAmount < min) totalAmount = min;
                }
            }

            return Math.max(totalAmount, 0.0);
        } catch (Exception e) {
            log.warn("calculateParkingPrice: {}", e.getMessage());
            return 0.0;
        }
    }

    private static String format(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(SC_DATE_FORMAT);
    }

    private static ScPayRequest parsePayRequest(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            pms_core.config.JSON parser = new pms_core.config.JSON(json);
            pms_core.config.JSON.Object obj = (pms_core.config.JSON.Object) parser.parseObject();
            if (obj.getString("plate") == null || obj.getString("txnId") == null || obj.getDouble("amount") == null) {
                return null;
            }
            ScPayRequest req = new ScPayRequest();
            req.setPlate(obj.getString("plate"));
            req.setTxnId(obj.getString("txnId"));
            req.setAmount(obj.getDouble("amount"));
            req.setCurrency(obj.getString("currency"));
            req.setMethod(obj.getString("method"));
            return req;
        } catch (Exception e) {
            return null;
        }
    }
}
