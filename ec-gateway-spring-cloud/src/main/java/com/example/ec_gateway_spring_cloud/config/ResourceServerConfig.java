package com.example.ec_gateway_spring_cloud.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

        private static final String[] PUBLIC = { "/actuator/**", "/ec-oauth/**", "/ec-user/register",
                        "/ec-cart/insert/**" };
        private static final String[] ADMIN = { "/ec-user/user/**", "/ec-config-server/**" };
        private static final String[] CLIENT_GET = { "/ec-orders/**", "/ec-cart/insert", "/ec-cart/cart/**",
                        "/ec-wishlist/insert", "/ec-wishlist/wishlist/**", "/ec-payment/**",
                        "/ec-product/**" };
        private static final String[] CLIENT_POST = { "/ec-orders/**", "/ec-cart/insert", "/ec-cart/cart/**",
                        "/ec-wishlist/insert", "/ec-wishlist/wishlist/**", "/ec-payment/**" };

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

                http
                                .authorizeExchange(
                                                auth -> auth
                                                                .pathMatchers(PUBLIC).permitAll()
                                                                .pathMatchers(ADMIN).hasAnyRole("ADMINISTRADOR")
                                                                .pathMatchers(HttpMethod.GET, CLIENT_GET)
                                                                .hasAnyRole("ADMINISTRADOR", "CLIENTE")
                                                                .pathMatchers(HttpMethod.POST, CLIENT_POST)
                                                                .hasAnyRole("ADMINISTRADOR", "CLIENTE")
                                                                .anyExchange().authenticated())
                                .csrf(
                                                csrf -> csrf.disable())
                                .oauth2ResourceServer(
                                                resource -> resource
                                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                                                jwtAuthenticationConverter())));

                return http.build();
        }

        private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
                JwtAuthenticationConverter jwtAuthenticator = new JwtAuthenticationConverter();

                jwtAuthenticator.setJwtGrantedAuthoritiesConverter(
                                jwt -> {
                                        List<String> authStringList = jwt.getClaimAsStringList("authorities");

                                        if (authStringList == null) {
                                                return Collections.emptyList();
                                        }

                                        JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();

                                        Collection<GrantedAuthority> grantedAuthorities = scopesConverter.convert(jwt);

                                        grantedAuthorities.addAll(authStringList.stream()
                                                        .map(SimpleGrantedAuthority::new)
                                                        .toList());

                                        return grantedAuthorities;
                                });

                return jwt -> {
                        return Mono.just(jwtAuthenticator.convert(jwt));
                };
        }

}
