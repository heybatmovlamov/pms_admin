package web_pms_core.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParkingRequest {

    private Integer organization;
    private String name;
    private String email;
    private Integer bitmask;
    private Integer places;
    private Integer tariff;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private byte[] image;
}
