package com.jack.authservice.service.impl;

import com.jack.authservice.dto.AuthRequestDTO;
import com.jack.authservice.dto.AuthResponseDTO;
import com.jack.authservice.dto.UserRegistrationDTO;
import com.jack.authservice.dto.UserResponseDTO;
import com.jack.authservice.entity.Users;
import com.jack.authservice.repository.UsersRepository;
import com.jack.authservice.security.JwtTokenProvider;
import com.jack.authservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UsersRepository usersRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDTO.getUsername(),
                            authRequestDTO.getPassword()
                    )
            );

            // Set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Generate JWT token
            String jwt = jwtTokenProvider.generateToken(authentication);
            logger.info("User '{}' successfully authenticated", authRequestDTO.getUsername());
            return new AuthResponseDTO(jwt);

        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user '{}': {}", authRequestDTO.getUsername(), e.getMessage());
            throw new BadCredentialsException("Invalid username or password.");
        } catch (Exception e) {
            logger.error("Unexpected error during authentication: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while processing your login request.");
        }
    }

    @Override
    public UserResponseDTO register(UserRegistrationDTO registrationDTO) {
        // Check if user already exists
        if (usersRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            logger.error("User registration failed. User with email '{}' already exists", registrationDTO.getEmail());
            throw new RuntimeException("User with email already exists.");
        }

        // Create new user, encrypt password
        Users newUser = Users.builder()
                .username(registrationDTO.getUsername())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))  // Encrypt password
                .build();

        // Save user to the repository
        Users savedUser = usersRepository.save(newUser);

        logger.info("User '{}' successfully registered", savedUser.getUsername());

        // Return user details in the response
        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }
}
