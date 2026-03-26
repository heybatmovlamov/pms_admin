package web_pms_core.model.local.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CameraPayload {
    private String plate;
    private String dateTime;
}

