package web_pms_core.model.local.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Unified SC API response (multi response) – same structure as PMS for agent compatibility.
 * Fields status and message always present; others depend on endpoint (check / status / pay).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScResponse {
    private String status;
    private String message;
    private String code;
    private String plate;
    private Double amount;
    private Double remaining;
    private String currency;
    private String entered;
    private String leaveTill;
}
