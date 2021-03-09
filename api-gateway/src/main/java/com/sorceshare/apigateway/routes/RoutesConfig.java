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

    private static final String USER_SIGN_IN_PATH = "/api/auth/signIn";
    private static final String USER_SIGN_UP_PATH = "/api/auth/signUp";
    private static final String AI_STREAM_INITIAL_LOAD = "/api/aiStream/initialLoadArticles";
    private static final String REAL_TIME_STREAM = "/api/aiStream/getStreamArticles";

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
                        .and()
                      .uri(routeProperties.getUrl().getUserStore())

        )
        .route(
                r -> r.path(USER_SIGN_IN_PATH)
                        .and()
                        .uri(routeProperties.getUrl().getUserStore())
        )
        .route(
                r -> r.path(AI_STREAM_INITIAL_LOAD)
                        .uri(routeProperties.getUrl().getAiFeedService())
        )
        .route(
                r -> r.path(REAL_TIME_STREAM)
                        .uri(routeProperties.getUrl().getAiFeedService())
        )
        .build();
    }
}
