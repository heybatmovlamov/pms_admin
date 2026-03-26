package web_pms_core.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OwnerResponse {

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
    private Integer active;
    private Integer status;
}
