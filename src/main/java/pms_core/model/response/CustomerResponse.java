package pms_core.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String lisence;
    private String phone;
    private String email;
    private String pinCode;
    private Integer counter;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String description;
    private String meta;
    private String data;
    private Integer kyc;
    private Integer active;
    private Integer status;
}
