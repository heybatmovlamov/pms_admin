package web_pms_core.model.request;

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
public class TransactionListRequest {

    private Integer offset = 0;
    private Integer limit = 10;
    private String method;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
