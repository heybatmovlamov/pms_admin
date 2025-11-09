package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles", schema = "pms_core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String plate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization")
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking")
    private ParkingsEntity parking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space")
    private SpacesEntity space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff", nullable = false)
    private TariffsEntity tariff;

    @Column(nullable = false)
    private LocalDateTime scanned;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    private LocalDateTime paid;

    @Column(name = "`exit`")
    private LocalDateTime exit;

    @Column
    private String action;

    @Column
    private String brand;

    @Column
    private String color;

    @Column
    private Integer owner;

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private Integer active = 1;

    @Column
    private Integer status = 1;
}
