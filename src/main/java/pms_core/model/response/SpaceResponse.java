package pms_core.model.response;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;

import java.time.LocalDateTime;

@Data
public class SpaceResponse {

    private Integer id;
    private String name;
    private Integer organization;
    private Integer parking;
    private Integer level;
    private String section;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer active;
    private Integer status;
}
