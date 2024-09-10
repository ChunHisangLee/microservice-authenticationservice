package com.jack.authservice.service.impl;

import com.jack.authservice.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final Set<String> tokenBlacklist = new HashSet<>();

    @Override
    public void invalidateToken(String token) {
        if (token != null && !token.trim().isEmpty()) {
            tokenBlacklist.add(token);
            logger.info("Token added to blacklist: {}", token);
        } else {
            logger.warn("Invalid token provided for invalidation.");
            throw new IllegalArgumentException("Token is invalid or empty.");
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
