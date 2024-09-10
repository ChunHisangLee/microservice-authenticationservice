package com.jack.authservice.controller;

import com.jack.authservice.dto.AuthRequestDTO;
import com.jack.authservice.dto.AuthResponseDTO;
import com.jack.authservice.service.AuthService;
import com.jack.authservice.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponse = authService.login(authRequestDTO);
        logger.info("User {} logged in successfully", authRequestDTO.getEmail());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
            try {
                tokenService.invalidateToken(token);  // Invalidate the token
                logger.info("Token invalidated successfully: {}", token);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error("Failed to invalidate token: {}", e.getMessage());
                return ResponseEntity.status(500).build();  // HTTP 500 Internal Server Error
            }
        } else {
            logger.warn("Invalid token provided for logout.");
            return ResponseEntity.badRequest().build();  // HTTP 400 Bad Request if no token found
        }
    }
}
