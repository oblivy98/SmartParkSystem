package com.exam.smartpark.security.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService{

    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();

    @Override
    public void blacklist(String token, Date expiration) {
        blacklistedTokens.put(token, expiration.getTime());
    }

    @Override
    public boolean isBlacklisted(String token) {
        Long expiry = blacklistedTokens.get(token);

        if (expiry == null) {
            return false;
        }

        if (expiry < System.currentTimeMillis()) {
            blacklistedTokens.remove(token);
            return false;
        }

        return true;
    }
}
