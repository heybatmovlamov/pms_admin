package pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "owners", schema = "pms_core")
@Data
public class OwnersEntity {

    @Id
    private Integer id;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private Integer tariff;
    private String name;
    private String plate;
    private Integer role;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private Integer active = 1;
    private Integer status = 1;
}
