package pms_core.model.request;

import lombok.Data;

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
