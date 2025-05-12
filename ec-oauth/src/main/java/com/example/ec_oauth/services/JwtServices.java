package com.example.ec_oauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class JwtServices {

    @Autowired
    private JwtDecoder decoder;

    public String getClaimFromToken(String token, String claim) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getClaimAsString(claim);
        } catch (JwtException e) {
            System.out.println("Jwt não codificado: " + e.getMessage());
            return null;
        }
    }
}
