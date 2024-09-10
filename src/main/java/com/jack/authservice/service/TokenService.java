package com.jack.authservice.service;

public interface TokenService {
    void invalidateToken(String token);

    boolean isTokenBlacklisted(String token);
}
