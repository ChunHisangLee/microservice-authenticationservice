package com.jack.authservice.service;

import com.jack.authservice.dto.AuthRequestDTO;
import com.jack.authservice.dto.AuthResponseDTO;
import com.jack.authservice.dto.UserRegistrationDTO;
import com.jack.authservice.dto.UserResponseDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO authRequestDTO);

    UserResponseDTO register(UserRegistrationDTO registrationDTO);  // New method
}
