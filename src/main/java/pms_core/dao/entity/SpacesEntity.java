package pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "spaces", schema = "pms_core")
@Data
public class SpacesEntity {

    @Id
    private Integer id;
    private String name;
    private Integer organization;
    private Integer parking;
    private Integer level;
    private String section;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer active = 1;
    private Integer status = 1;
}
