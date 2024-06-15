package com.jwt.util.controller;

import com.jwt.util.domain.UserDetails;
import com.jwt.util.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok(userService.getUserDetails(httpHeaders));
    }
}
