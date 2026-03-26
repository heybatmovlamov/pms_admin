package web_pms_core.model.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserRequest {

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
    private byte[] image;
}
