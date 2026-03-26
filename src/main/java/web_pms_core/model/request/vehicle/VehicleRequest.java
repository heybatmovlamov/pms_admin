package web_pms_core.model.request.vehicle;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleRequest {

    private String plate;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private Integer tariff;
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
    private Integer active;
    private Integer status;
}
