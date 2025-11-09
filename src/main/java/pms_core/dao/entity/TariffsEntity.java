package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import pms_core.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "tariffs", schema = "pms_core")
@Data
public class TariffsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization", referencedColumnName = "id", nullable = false)
    private OrganizationsEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking", referencedColumnName = "id")
    private ParkingsEntity parking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space", referencedColumnName = "id")
    private SpacesEntity space;

    // --- Enum Field ---
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleType vehicleType;

    // --- Duration Fields ---
    @Column(name = "duration_start", nullable = false)
    private Integer durationStart;

    @Column(name = "duration_time", nullable = false)
    private Integer durationTime;

    @Column(name = "duration_leave", nullable = false)
    private Integer durationLeave;

    @Column
    private BigDecimal rate;

    @Column
    private BigDecimal min;

    @Column
    private BigDecimal max;

    @Column(length = 10)
    private String currency = "AZN";

    @Column
    private String validDays ;

    @Column(name = "start_time")
    private LocalTime startTime = LocalTime.of(0, 0, 0);

    @Column(name = "end_time")
    private LocalTime endTime = LocalTime.of(23, 59, 59);

    @Column
    private BigDecimal discount = BigDecimal.ZERO;

    @Column
    private BigDecimal extraCharge = BigDecimal.ZERO;

    @Column
    private String description;

    @Column
    private Integer active = 1;

    @Column
    private Integer status = 1;
}
