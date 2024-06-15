package com.jwt.util.service;

import com.jwt.util.domain.UserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class UserService {

    private JWTService jwtService;

    public UserService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public UserDetails getUserDetails(HttpHeaders httpHeaders) {
        List<String> authHeader = httpHeaders.get("Authorization");
        if (CollectionUtils.isEmpty(authHeader) || authHeader.get(0).isBlank() || !authHeader.get(0).startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request is unauthorized");
        }
        String jwtToken = authHeader.get(0).substring(7);

        boolean isValid = jwtService.validateJWT(jwtToken);

        if (isValid) {
            return new UserDetails("agniraj", "Agniraj", "M");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request is unauthorized");
        }
    }
}
