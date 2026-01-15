package pms_core.model.response;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.SpacesEntity;
import pms_core.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class TariffResponse {

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
    private String currency;
    private String validDays;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal discount;
    private BigDecimal extraCharge;
    private String description;
    private Integer active;
    private Integer status;
}
