package pms_core.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Integer id;
    private Integer organization;
    private Integer parking;
    private Integer customer;
    private Integer vehicle;
    private String vehiclePlate;
    private Integer tariff;
    private Integer totalDuration;
    private BigDecimal amount;
    private String currency;
    private String txnId;
    private String reference;
    private LocalDateTime datetime;
    private String method;
    private String description;
    private Integer status;
}
