package pms_core.model.local.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * CC API response: HTTP status + body. All logic lives in service; controller only forwards this.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CcResponse {
    private HttpStatus httpStatus;
    private CcResult body;
}
