package com.jwt.util.controller;

import com.jwt.util.domain.LoginRequest;
import com.jwt.util.domain.UserDetails;
import com.jwt.util.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userAuthService.login(loginRequest));
    }

}
