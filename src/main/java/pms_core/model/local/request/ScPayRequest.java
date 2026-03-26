package pms_core.model.local.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScPayRequest {
    private String plate;
    private String txnId;
    private Double amount;
    private String currency;
    private String method;
}
