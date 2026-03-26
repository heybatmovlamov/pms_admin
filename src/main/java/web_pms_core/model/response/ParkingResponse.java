package web_pms_core.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkingResponse {

    private Integer id;
    private Integer organization;
    private String name;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer bitmask;
    private Integer places;
    private Integer tariff;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private byte[] image;
    private Integer active;
    private Integer status;
}
