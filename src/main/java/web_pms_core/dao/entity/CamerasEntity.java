package web_pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "cameras")
@Data
public class CamerasEntity {

    @Id
    private Integer id;
    private String name;
    private String ip;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private String type;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer active = 1;
    private Integer status = 1;
}
