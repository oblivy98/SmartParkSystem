package com.exam.smartpark.user.service;

import com.exam.smartpark.security.service.JwtService;
import com.exam.smartpark.security.service.TokenBlacklistService;
import com.exam.smartpark.user.dto.LoginRequest;
import com.exam.smartpark.user.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmartParkUserServiceImpl implements SmartParkUserService{

    private static final Logger log = LoggerFactory.getLogger((SmartParkUserServiceImpl.class));

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenBlacklistService tokenBlacklistService;

    public SmartParkUserServiceImpl(AuthenticationManager authenticationManager,
                                    JwtService jwtService,
                                    TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }
    @Override
    public LoginResponse login(LoginRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        ));

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponse(accessToken, user.getUsername());
    }

    @Override
    public void logout(HttpServletRequest request) {
        blacklistAccessToken(request);
    }

    private void blacklistAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ") || header.length() <= 7) {
            return;
        }

        String token = header.substring(7).trim();

        if (token.isEmpty()) {
            return;
        }

        try {
            Date expiration = jwtService.extractAccessClaims(token).getExpiration();
            tokenBlacklistService.blacklist(token, expiration);
        } catch (Exception e) {
            log.error("There is an issue on blacklisting the token");
        }
    }
}
