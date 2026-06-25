package com.exam.smartpark.security.service;

import java.util.Date;

public interface TokenBlacklistService {

    void blacklist(String token, Date expiration);

    boolean isBlacklisted(String token);
}
