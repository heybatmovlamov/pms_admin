package pms_core.model.response;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.SpacesEntity;
import pms_core.dao.entity.TariffsEntity;

import java.time.LocalDateTime;

@Data
public class VehicleResponse {

    private Integer id;
    private String plate;
    private OrganizationsEntity organization;
    private ParkingsEntity parking;
    private SpacesEntity space;
    private TariffsEntity tariff;
    private LocalDateTime scanned;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime paid;
    private LocalDateTime exit;
    private String action;
    private String brand;
    private String color;
    private Integer owner;
    private String type;
    private String description;
    private Integer active;
    private Integer status;
}
