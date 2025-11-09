package pms_core.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CameraRequest {

    private String name;
    private String ip;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private String type;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer active;
    private Integer status;
}
