package com.sorceshare.apigateway.routes;

import com.sorceshare.apigateway.utils.GatewayRouteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class RoutesConfig {

    private static final String USER_SIGN_IN_PATH = "/SignIn";
    private static final String USER_SIGN_UP_PATH = "/SignUp/**";

    @Autowired
    private RouteProperties routeProperties;

    @PostConstruct
    public void init(){
       GatewayRouteUtils.routeProperties = this.routeProperties;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        log.info("Create Route Locate Builder");
        return builder.routes()
        .route(
                r -> r.path(USER_SIGN_UP_PATH)
                      .uri(routeProperties.getUserStore().getUrl().getSignUp())
        )
        .route(
                r -> r.path(USER_SIGN_IN_PATH)
                        .and()
                        .uri(routeProperties.getUserStore().getUrl().getSignIn())
        )
        .build();
    }
}
