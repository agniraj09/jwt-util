package com.jwt.util.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JWTService {

    private static final String SECRET_KEY = "ciS8MZoTF8RDMphU23z3C9Gn4UbHqjLOAqb3uG8ctiWEwb4H3FHNu0h11t9srnCcBnBWWN+3emZRYnahciB7BQ==";
    private static final long VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(10);

    public String generateJWT(String username) {
        String jwt = Jwts.builder()
                .header().add("Content-type", "encoded")
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

    private static SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateJWT(String jwtToken) {
        try {
            Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwtToken).getPayload();
            return claims.getExpiration().after(Date.from(Instant.now()));
        } catch (SignatureException e) {
            log.error("JWT is tampered");
            return false;
        }
    }
}
