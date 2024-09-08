package com.jack.authservice.service.impl;

import com.jack.authservice.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        // Authenticate user and generate JWT token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        String token = jwtTokenProvider.generateToken(authentication);
        return new AuthResponseDTO(token);
    }
}
