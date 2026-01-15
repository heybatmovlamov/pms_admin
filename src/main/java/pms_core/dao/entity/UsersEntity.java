package pms_core.dao.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "users", schema = "pms_core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
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
    private Integer status = 1;
}
