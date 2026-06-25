package com.exam.smartpark.user.controller;

import com.exam.smartpark.user.dto.LoginRequest;
import com.exam.smartpark.user.dto.LoginResponse;
import com.exam.smartpark.user.service.SmartParkUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SmartParkUserController {

    private final SmartParkUserService smartParkUserService;

    public SmartParkUserController(SmartParkUserService smartParkUserService) {
        this.smartParkUserService = smartParkUserService;
    }

    @PostMapping(   value = "/login",
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(smartParkUserService.login(request, servletRequest));
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        smartParkUserService.logout(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
