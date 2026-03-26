package pms_core.model.local.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CC API result body. Only "result" for outcome (entered, leaved, BLOCKED, NOT PAID, READY, etc.); no status field.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CcResult {
    private String result;
    private String plate;
    private String scanned;
    private String created;
    private String tariff;
    private String action;
}
