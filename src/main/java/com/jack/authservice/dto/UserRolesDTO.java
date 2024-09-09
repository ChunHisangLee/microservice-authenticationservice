package com.jack.authservice.dto;

import lombok.Data;

@Data
public class UserRolesDTO {
    private Long id;
    private Long userId;
    private String role;
}
