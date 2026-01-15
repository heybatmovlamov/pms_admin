package pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pms_core.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalTime;

@Table(name = "tariffs", schema = "pms_core")
@Data
public class TariffsEntity {

    @Id
    private Integer id;
    private String name;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private VehicleType vehicleType;
    private Integer durationStart;
    private Integer durationTime;
    private Integer durationLeave;
    private BigDecimal rate;
    private BigDecimal min;
    private BigDecimal max;
    private String currency = "AZN";
    private String validDays ;
    private LocalTime startTime = LocalTime.of(0, 0, 0);
    private LocalTime endTime = LocalTime.of(23, 59, 59);
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal extraCharge = BigDecimal.ZERO;
    private String description;
    private Integer active = 1;
    private Integer status = 1;
}
