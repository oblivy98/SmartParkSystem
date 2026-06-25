package com.exam.smartpark.user.service;

import com.exam.smartpark.user.dto.LoginRequest;
import com.exam.smartpark.user.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface SmartParkUserService {

    LoginResponse login(LoginRequest request, HttpServletRequest servletRequest);

    void logout(HttpServletRequest request);
}
