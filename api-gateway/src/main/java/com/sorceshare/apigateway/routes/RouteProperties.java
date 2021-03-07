package com.sorceshare.apigateway.routes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "routes")
public class RouteProperties {
    private UserStore userStore;

    @Getter
    @Setter
    public static class UserStore{
        private UserStoreURL url;

        @Setter
        @Getter
        public static class UserStoreURL {
            private String signUp;
            private String signIn;
        }
    }
}
