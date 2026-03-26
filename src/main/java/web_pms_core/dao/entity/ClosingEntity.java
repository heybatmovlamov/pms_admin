package web_pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "closing_time")
@Data
public class ClosingEntity {

    @Id
    private Integer id;

    private LocalDateTime closingTime;
    private String description;
}
