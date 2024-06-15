package com.jwt.util.domain;

public record UserDetails(
        String username,
        String firstName,
        String lastName
) {
}
