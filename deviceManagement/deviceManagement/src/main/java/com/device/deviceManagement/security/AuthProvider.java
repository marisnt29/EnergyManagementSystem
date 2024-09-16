package com.device.deviceManagement.security;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.device.deviceManagement.services.AccountServiceImpl;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;


@RequiredArgsConstructor
@Component
public class AuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey ;

    private final AccountServiceImpl userService;

    @PostConstruct
    protected void init() {

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }



    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        System.out.println("token is :" + token);
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);
        System.out.println("Decoded subject:"+decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(decoded.getSubject(), null, Collections.emptyList());
    }

}

