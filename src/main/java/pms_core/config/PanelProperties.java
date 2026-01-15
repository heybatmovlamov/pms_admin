package pms_core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "panel")
@Getter
@Setter
public class PanelProperties {

    private String uri;
    private String speed;
    private int delay = 2000;
}
