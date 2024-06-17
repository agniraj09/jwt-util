package com.jwt.util.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JWTService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private static final long VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(10);

    public String generateJWT(String username) {
        String jwt = Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY_DURATION)))
                .issuer("https://agniraj.com")
                .signWith(getSecretKey())
                .compact();
        log.info("JWT Token {}", jwt);
        return jwt;
    }

    public boolean validateJWT(String jwtToken) {
        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwtToken).getPayload();
        return claims.getExpiration().after(Date.from(Instant.now()));

    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
