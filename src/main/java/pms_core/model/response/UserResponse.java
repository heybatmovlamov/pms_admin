package pms_core.model.response;

import lombok.Data;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;

import java.sql.Timestamp;

@Data
public class UserResponse {

    private Integer id;
    private OrganizationsEntity organization;
    private ParkingsEntity parking;
    private String name;
    private String surname;
    private Timestamp birthdate;
    private String gender;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private Integer role;
    private String language;
    private Integer bitmask;
    private Timestamp created;
    private Timestamp modified;
    private byte[] image;
    private Integer status;
}
