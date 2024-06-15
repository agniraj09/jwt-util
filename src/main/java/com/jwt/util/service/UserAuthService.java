package com.jwt.util.service;

import com.jwt.util.domain.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserAuthService {

    private JWTService jwtService;

    public UserAuthService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public String login(LoginRequest loginRequest) {
        return jwtService.generateJWT(loginRequest.username());
    }


}
