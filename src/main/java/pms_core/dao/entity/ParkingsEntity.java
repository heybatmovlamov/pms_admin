package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parkings", schema = "pms_core")
@Data
public class ParkingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization", referencedColumnName = "id")
    private OrganizationsEntity organization;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    @Column
    private Integer bitmask;

    @Column
    private Integer places;

    @Column
    private Integer tariff;

    @Column(length = 400)
    private String location;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(length = 200)
    private String description;

    @Lob
    private byte[] image;

    @Column
    private Integer active = 1;

    @Column
    private Integer status = 1;
}
