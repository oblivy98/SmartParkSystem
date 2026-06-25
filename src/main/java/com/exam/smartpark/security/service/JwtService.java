package com.exam.smartpark.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(UserDetails userDetails);

    Claims extractAccessClaims(String token);

    boolean isAccessTokenValid(String token);

    String extractUsername(String token);
}
