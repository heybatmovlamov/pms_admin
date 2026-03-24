package pms_core.model.request.vehicle;

import lombok.Data;

@Data
public class VehicleUpdateRequest {

    private String plate;
    private String brand;
}
