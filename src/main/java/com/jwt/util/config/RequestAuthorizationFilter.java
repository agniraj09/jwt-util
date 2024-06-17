package com.jwt.util.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.util.service.JWTService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Order(1)
@Component
public class RequestAuthorizationFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private ObjectMapper objectMapper;

    public RequestAuthorizationFilter(JWTService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                boolean isValid = validateJWTToken(request);

                if (isValid) {
                    filterChain.doFilter(request, response);
                } else {
                    throw new JwtException("Request is unauthorized");
                }
            } catch (JwtException e) {
                var detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
                log.error("Error while validating JWT", e);
                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(objectMapper.writeValueAsString(detail));
            }
        }
    }

    private boolean validateJWTToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("JWT token missing. Request is unauthorized");
        }
        String jwtToken = authHeader.substring(7);
        return jwtService.validateJWT(jwtToken);
    }
}
