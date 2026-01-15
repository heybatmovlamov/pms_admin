package pms_core.model.request;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;

import java.time.LocalDateTime;

@Data
public class SpaceRequest {

    private String name;
    private Integer organization;
    private Integer parking;
    private Integer level;
    private String section;
    private String description;
}
