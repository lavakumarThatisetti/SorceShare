package com.sorceshare.apigateway.routes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "routes")
public class RouteProperties {
    private URL url;

    @Getter
    @Setter
    public static class URL{
        private String userStore;
        private String aiFeedService;
    }
}
