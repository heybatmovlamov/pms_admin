package pms_core.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
