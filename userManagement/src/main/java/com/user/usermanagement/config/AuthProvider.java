package com.user.usermanagement.config;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.user.usermanagement.dtos.AccountDTO;
import com.user.usermanagement.services.AccountServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import static org.springframework.security.config.Elements.JWT;

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

    public String createToken(String login) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return com.auth0.jwt.JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        AccountDTO user = userService.getAccountByName(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}

