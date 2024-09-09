package com.jack.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final SecretKey secretKey;
    private final int jwtExpirationMs;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService,
                            @Value("${app.jwtSecret}") String jwtSecret,
                            @Value("${app.jwtExpirationMs}") int jwtExpirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())   // Use email instead of username
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey)
                .compact();
    }


    // Extract email from the JWT token
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();  // Email is the subject in this case
    }

    // Extract all claims from the JWT token
    public Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        // Parse the claims from the token
        return parser.parseSignedClaims(token).getPayload();
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();

            // Parse and validate the token (will throw an exception if invalid)
            parser.parse(token);
            return true;
        } catch (Exception ex) {
            // Handle the exception (log or handle the error as needed)
            System.err.println("Invalid JWT token: " + ex.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        // Extract the email (not username) from the token
        String email = getEmailFromToken(token);

        // Load the user details using the email
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);  // Here, "username" actually refers to email

        // Create an Authentication object with the user details and token (no credentials are passed)
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
