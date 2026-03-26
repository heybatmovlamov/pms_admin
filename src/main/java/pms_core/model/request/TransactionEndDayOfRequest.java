package pms_core.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * List request for transactions. Result is always sorted by datetime descending (newest first).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEndDayOfRequest {

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 20;

    private String type;
}
