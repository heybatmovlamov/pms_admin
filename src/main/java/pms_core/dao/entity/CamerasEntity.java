package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cameras", schema = "pms_core")
@Data
public class CamerasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization", referencedColumnName = "id")
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking", referencedColumnName = "id")
    private ParkingsEntity parking;

    @Column
    private Integer space;

    @Column(length = 100)
    private String type;

    @Column(length = 200)
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    @Column
    private Integer active = 1;

    @Column
    private Integer status = 1;
}
