package web_pms_core.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraRequest {

    private String name;
    private String ip;
    private Integer organization;
    private Integer parking;
    private Integer space;
    private String type;
    private String description;
}
