package com.example.ec_oauth.resources;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_oauth.entities.Role;
import com.example.ec_oauth.entities.UserEntity;
import com.example.ec_oauth.entities.records.LoginRequest;
import com.example.ec_oauth.entities.records.LoginResponse;
import com.example.ec_oauth.feignclient.UsersFeignClient;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping("/login")
public class LoginResource {

    @Autowired
    private JwtEncoder encoder;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsersFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        UserEntity user = userFeignClient.findByEmail(req.email()).getBody();

        if (user.equals(null) || !passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Instant now = Instant.now();

        String authorities = user.getRoles().stream().map(Role::getRole).collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Ec-Oauth")
                .subject(user.getEmail())
                .issuedAt(now)
                .claim("authorities ", authorities)
                .expiresAt(now.plusSeconds(500))
                .build();

        String jwt = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

}
