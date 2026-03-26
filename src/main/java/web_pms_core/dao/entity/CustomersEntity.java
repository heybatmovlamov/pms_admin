package web_pms_core.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersEntity {

    @Id
    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String lisence;
    private String phone;
    private String email;
    @Column("pin_code")
    private String pinCode;
    @Builder.Default
    private Integer counter = 0;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String description;
    private String meta;
    private String data;
    @Builder.Default
    private Integer kyc = 0;
    @Builder.Default
    private Integer active = 1;
    @Builder.Default
    private Integer status = 1;
}
