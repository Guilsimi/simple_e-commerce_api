package com.example.ec_gateway_spring_cloud.config;

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
                                .route("order-route", r -> r.path("/ec-order/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://EC-ORDER"))
                                .route("user-route", r -> r.path("/ec-user/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://EC-USER"))
                                .build();
        }

}
