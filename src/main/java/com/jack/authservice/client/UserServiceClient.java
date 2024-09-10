package com.jack.authservice.client;

import com.jack.authservice.dto.AuthRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://127.0.0.1:8081")
public interface UserServiceClient {

    @PostMapping("/api/users/verify-password")
    Boolean verifyPassword(@RequestBody AuthRequestDTO authRequestDTO);
}
