package com.jack.authservice.mapper;

import com.jack.authservice.dto.UsersDTO;
import com.jack.authservice.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    public UsersDTO toDto(Users users) {
        return UsersDTO.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .build();
    }

    public Users toEntity(UsersDTO usersDTO) {
        return Users.builder()
                .username(usersDTO.getUsername())
                .email(usersDTO.getEmail())
                .password(usersDTO.getPassword()) // Ensure password is mapped if provided
                .build();
    }
}
