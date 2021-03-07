package com.sorceshare.userstore.config;


import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Value("${swagger.application.description}")
    private String appDescription = null;

    @Value("${swagger.application.name}")
    private String appName =  null;

    @Value("${swagger.application.version}")
    private String appVersion =  null;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title(appName)
                .version(appVersion)
                .description(appDescription));
    }
}
