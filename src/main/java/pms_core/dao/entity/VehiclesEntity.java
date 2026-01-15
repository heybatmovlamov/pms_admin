package pms_core.dao.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name = "vehicles", schema = "pms_core")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesEntity {

    @Id
    private Integer id;
    private String plate;
    private Integer  organization;
    private Integer  parking;
    private Integer  space;
    private Integer  tariff;
    private LocalDateTime scanned;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime paid;
    private LocalDateTime exit;
    private String action;
    private String brand;
    private String color;
    private Integer owner;
    private String type;
    private String description;
    private Integer active = 1;
    private Integer status = 1;
}
