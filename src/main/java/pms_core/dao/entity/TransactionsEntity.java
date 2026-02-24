package pms_core.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "transactions", schema = "pms_core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsEntity {

    @Id
    private Integer id;
    private Integer organization;
    private Integer parking;
    private Integer customer;
    private Integer vehicle;
    @Column("vehicle_plate")
    private String vehiclePlate;
    private Integer tariff;
    @Column("total_duration")
    private Integer totalDuration;
    private BigDecimal amount;
    private String currency;
    @Column("txn_id")
    private String txnId;
    private String reference;
    private LocalDateTime datetime;
    private String method;
    private String description;
    private Integer status;
}
