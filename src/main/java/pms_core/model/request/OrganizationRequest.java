package pms_core.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrganizationRequest {

    private String name;
    private String tel;
    private String email;
    private String biller;
    private String merchant;
    private String secret;
    private String tplMob;
    private String tplWeb;
    private Integer bitmask;
    private String description;
    private byte[] image;
}
