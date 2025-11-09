package pms_core.model.response;

import jakarta.persistence.*;
import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.SpacesEntity;
import pms_core.dao.entity.TariffsEntity;

import java.time.LocalDateTime;

@Data
public class OwnerResponse {

    private Integer id;
    private OrganizationsEntity organization;
    private ParkingsEntity parking;
    private SpacesEntity space;
    private TariffsEntity tariff;
    private String name;
    private String plate;
    private Integer role;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private Integer active;
    private Integer status;
}
