package com.jack.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user by email from UserService
        String url = "http://userservice/api/users/email/" + email;
        UserDetails userDetails = restTemplate.getForObject(url, UserDetails.class);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return userDetails;
    }
}
