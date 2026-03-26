package pms_core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "camera")
@Getter
@Setter
public class CameraProperties {

    private String didoUri;
    private String didoUsername;
    private String didoPassword;

    private Vivotek vivotek = new Vivotek();

    @Getter
    @Setter
    public static class Vivotek {
        private String username;
        private String password;
    }
}

