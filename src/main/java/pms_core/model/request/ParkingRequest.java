package pms_core.model.request;

import jakarta.persistence.*;
import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkingRequest {

    private OrganizationsEntity organization;
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
