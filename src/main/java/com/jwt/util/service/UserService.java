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

    public UserDetails getUserDetails(HttpHeaders httpHeaders) {
        return new UserDetails("agniraj", "Agniraj", "M");
    }
}
