package pms_core.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizations", schema = "pms_core")
@Data
public class OrganizationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String tel;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String biller;

    @Column(length = 100)
    private String merchant;

    @Column(length = 100)
    private String secret;

    @Column(name = "tpl_mob", length = 100)
    private String tplMob;

    @Column(name = "tpl_web", length = 100)
    private String tplWeb;

    @Column
    private Integer bitmask;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime modified;

    @Column(length = 200)
    private String description;

    @Lob
    @Column
    private byte[] image;

    @Column
    private Integer status = 1;
}