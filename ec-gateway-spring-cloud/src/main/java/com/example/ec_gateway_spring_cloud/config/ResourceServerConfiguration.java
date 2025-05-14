package com.example.ec_gateway_spring_cloud.config;

import java.security.interfaces.RSAPublicKey;
import java.util.Set;

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

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfiguration {

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
        public ReactiveJwtDecoder decoder(
                        @Value("${jwt.public-key}") RSAPublicKey publicKey) {
                return NimbusReactiveJwtDecoder.withPublicKey(publicKey)
                                .jwtProcessorCustomizer(processor -> processor.setJWTClaimsSetVerifier(
                                                new DefaultJWTClaimsVerifier<>(
                                                                new JWTClaimsSet.Builder()
                                                                                .issuer("ec-oauth").build(),
                                                                Set.of("sub", "exp", "iat", "authorities"))))
                                .build();
        }

}
