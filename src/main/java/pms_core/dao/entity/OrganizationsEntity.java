package pms_core.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "organizations")
@Data
public class OrganizationsEntity {

    @Id
    private Integer id;
    private String name;
    private String tel;
    private String email;
    private String biller;
    private String merchant;
    private String secret;
    private String tplMob;
    private String tplWeb;
    private Integer bitmask;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String description;
    private byte[] image;
    private Integer status = 1;
}