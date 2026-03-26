package web_pms_core.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String access_token;
    private String refresh_token;
}
