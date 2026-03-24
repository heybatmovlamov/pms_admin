package pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "parkings")
@Data
public class ParkingsEntity {

    @Id
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
    private Integer active = 1;
    private Integer status = 1;
}
