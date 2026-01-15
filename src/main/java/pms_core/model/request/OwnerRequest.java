package pms_core.model.request;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.SpacesEntity;
import pms_core.dao.entity.TariffsEntity;

import java.time.LocalDateTime;

@Data
public class OwnerRequest {

    private Integer organization;
    private Integer parking;
    private Integer space;
    private Integer tariff;
    private String name;
    private String plate;
    private Integer role;
    private String description;
}
