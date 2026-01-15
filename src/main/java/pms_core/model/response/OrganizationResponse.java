package pms_core.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrganizationResponse {

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
    private Integer status;
}
