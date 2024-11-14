package com.example.ec_oauth.config;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.example.ec_oauth.config.properties.AuthProperties;
import com.example.ec_oauth.entities.UserEntity;
import com.example.ec_oauth.feignclient.UsersFeignClient;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class WebSecurityConfig {

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
                return http.formLogin(Customizer.withDefaults()).build();
        }

        @Bean
        public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(
                                                req -> req
                                                                .anyRequest()
                                                                .authenticated());

                return http.formLogin(Customizer.withDefaults()).build();
        }

        @Bean
        public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextCustomizer(UsersFeignClient usersFeignClient) {
                return (context -> {
                        Authentication authentication = context.getPrincipal();
                        if (authentication.getPrincipal() instanceof User) {
                                final User user = (User) authentication.getPrincipal();

                                final UserEntity userEntity = usersFeignClient.findByEmail(user.getUsername())
                                                .getBody();

                                Set<String> authorities = new HashSet<>();
                                for (GrantedAuthority auth : user.getAuthorities()) {
                                        authorities.add(auth.toString());
                                }

                                context.getClaims().claim("user_id", userEntity.getId().toString());
                                context.getClaims().claim("user_name", userEntity.getName());
                                context.getClaims().claim("authorities", authorities);
                        }

                });
        }

        @Bean
        public RegisteredClientRepository clientRepository() {
                RegisteredClient userClient = RegisteredClient
                                .withId("1")
                                .clientId("myappid")
                                .clientSecret(passwordEncoder.encode("myappsecret"))
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                                .scope("users:read")
                                .scope("users:write")
                                .tokenSettings(
                                                TokenSettings.builder()
                                                                .accessTokenTimeToLive(Duration.ofSeconds(84600))
                                                                .build())
                                .build();

                RegisteredClient authClient = RegisteredClient
                                .withId("2")
                                .clientId("admclient")
                                .clientSecret(passwordEncoder.encode("adm123"))
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                .redirectUri("http://localhost:3000/authorized")
                                .redirectUri("https://oauthdebugger.com/debug")
                                .redirectUri("https://oauth.pstmn.io/v1/callback")
                                .scope("myuser:read")
                                .scope("myuser:write")
                                .tokenSettings(
                                                TokenSettings.builder()
                                                                .accessTokenTimeToLive(Duration.ofMinutes(15))
                                                                .refreshTokenTimeToLive(Duration.ofDays(1))
                                                                .reuseRefreshTokens(false)
                                                                .build())
                                .clientSettings(
                                                ClientSettings.builder()
                                                                .requireAuthorizationConsent(false)
                                                                .build())
                                .build();

                return new InMemoryRegisteredClientRepository(
                                Arrays.asList(userClient, authClient));
        }

        @Bean
        public AuthorizationServerSettings providerSettings(AuthProperties properties) {
                return AuthorizationServerSettings.builder()
                                .issuer(properties.getProviderUri())
                                .build();
        }

        @Bean
        public JWKSet jwkSet(AuthProperties properties) throws Exception {
                final var jksProperties = properties.getJks();
                final String jksPath = jksProperties.getPath();
                final InputStream inputStream = new ClassPathResource(jksPath).getInputStream();

                final KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(inputStream, jksProperties.getStorepass().toCharArray());

                RSAKey rsaKey = RSAKey.load(
                                keyStore,
                                jksProperties.getAlias(),
                                jksProperties.getKeypass().toCharArray());

                return new JWKSet(rsaKey);
        }

        @Bean
        public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
                return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
        }

        @Bean
        public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
                return new NimbusJwtEncoder(jwkSource);
        }
}
