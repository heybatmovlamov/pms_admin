package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "owners", schema = "pms_core")
@Data
public class OwnersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization", referencedColumnName = "id", nullable = false)
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking", referencedColumnName = "id", nullable = false)
    private ParkingsEntity parking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space", referencedColumnName = "id", nullable = false)
    private SpacesEntity space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff", referencedColumnName = "id", nullable = false)
    private TariffsEntity tariff;

    @Column(length = 100)
    private String name;

    @Column(length = 200)
    private String plate;

    @Column(nullable = false)
    private Integer role;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updated;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private Integer active = 1;

    @Column(nullable = false)
    private Integer status = 1;
}
