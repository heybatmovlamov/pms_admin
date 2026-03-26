package pms_core.model.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Integer id;
    private Integer organization;
    private Integer parking;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String gender;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private Integer role;
    private String language;
    private Integer bitmask;
    private LocalDateTime created;
    private LocalDateTime modified;
    private byte[] image;
    private Integer status;
}
