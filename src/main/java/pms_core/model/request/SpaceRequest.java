package pms_core.model.request;

import lombok.Data;

@Data
public class SpaceRequest {

    private String name;
    private Integer organization;
    private Integer parking;
    private Integer level;
    private String section;
    private String description;
}
