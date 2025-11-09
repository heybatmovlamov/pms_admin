package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "users", schema = "pms_core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization")
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking")
    private ParkingsEntity parking;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column
    private Timestamp birthdate;

    @Column(length = 10)
    private String gender;

    @Column(length = 20)
    private String mobile;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String password;

    private Integer role;

    @Column(length = 2)
    private String language;

    private Integer bitmask;

    @Column
    @CreationTimestamp
    private Timestamp created;

    @Column
    @UpdateTimestamp
    private Timestamp modified;

    @Lob
    private byte[] image;

    private Integer status = 1;
}
