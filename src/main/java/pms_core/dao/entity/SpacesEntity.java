package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "spaces", schema = "pms_core")
@Data
public class SpacesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization", referencedColumnName = "id")
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking", referencedColumnName = "id")
    private ParkingsEntity parking;

    @Column
    private Integer level;

    @Column(length = 100)
    private String section;

    @Column(length = 500)
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
