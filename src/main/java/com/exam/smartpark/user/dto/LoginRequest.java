package com.exam.smartpark.user.dto;

public record LoginRequest(
        String username,
        String password
) {}
