package com.example.ec_gateway_spring_cloud.config;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

        @Value("${jwt.public-key}")
        private RSAPublicKey publicKey;

        private static final String[] PUBLIC = { "/actuator/**", "/ec-oauth/**",
                        "/ec-user/register",
                        "/ec-cart/insert/**" };
        private static final String[] ADMIN = { "/ec-user/user/**", "/ec-config-server/**" };

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

                http
                                .authorizeExchange(
                                                auth -> auth
                                                                .pathMatchers(PUBLIC).permitAll()
                                                                .pathMatchers(ADMIN).hasAnyRole("ADMINISTRADOR")
                                                                .anyExchange().authenticated())
                                .csrf(csrf -> csrf.disable())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

                return http.build();
        }

        @Bean
        public ReactiveJwtDecoder decoder() {
                return NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();
        }

}
