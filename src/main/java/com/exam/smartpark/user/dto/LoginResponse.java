package com.exam.smartpark.user.dto;

public record LoginResponse (
        String token,
        String username
){}
