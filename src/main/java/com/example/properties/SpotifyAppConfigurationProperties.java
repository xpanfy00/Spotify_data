package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix ="com.example")
public class SpotifyAppConfigurationProperties {
    private App app = new App();

    @Data
    public class App {
        private String clientId;
        private String redirectUrl;
        private String scope;
    }
}
