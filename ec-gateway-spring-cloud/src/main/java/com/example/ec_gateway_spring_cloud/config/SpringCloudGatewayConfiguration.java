package com.example.ec_gateway_spring_cloud.config;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayConfiguration {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route("product-route", r -> r.path("/ec-product/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://EC-PRODUCTS"))
                                .route("orders-route", r -> r.path("/ec-orders/**")
                                                .filters(f -> f.stripPrefix(1)
                                                                .addResponseHeader(HttpHeaders.SET_COOKIE,
                                                                                "cookieName=cookieValue; Path=/; HttpOnly"))
                                                .uri("lb://EC-ORDERS"))
                                .route("user-route", r -> r.path("/ec-user/**")
                                                .filters(f -> f.stripPrefix(1)
                                                                .addResponseHeader(HttpHeaders.SET_COOKIE,
                                                                                "cookieName=cookieValue; Path=/; HttpOnly"))
                                                .uri("lb://EC-USER"))
                                .route("auth-route", r -> r.path("/ec-oauth/**")
                                                .filters(f -> f.stripPrefix(1)
                                                                .addResponseHeader(HttpHeaders.SET_COOKIE,
                                                                                "cookieName=cookieValue; Path=/; HttpOnly"))
                                                .uri("lb://EC-OAUTH"))
                                .build();
        }

}
