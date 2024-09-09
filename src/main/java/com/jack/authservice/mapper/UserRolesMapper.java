package com.jack.authservice.mapper;

import com.jack.authservice.dto.UserRolesDTO;
import com.jack.authservice.entity.UserRoles;
import org.springframework.stereotype.Component;

@Component
public class UserRolesMapper {

    public UserRolesDTO toDTO(UserRoles userRoles) {
        if (userRoles == null) {
            return null;
        }

        UserRolesDTO userRolesDTO = new UserRolesDTO();
        userRolesDTO.setId(userRoles.getId());
        userRolesDTO.setUserId(userRoles.getUser().getId());
        userRolesDTO.setRole(userRoles.getRole());

        return userRolesDTO;
    }

    public UserRoles toEntity(UserRolesDTO userRolesDTO) {
        if (userRolesDTO == null) {
            return null;
        }

        UserRoles userRoles = new UserRoles();
        userRoles.setId(userRolesDTO.getId());
        userRoles.setRole(userRolesDTO.getRole());

        return userRoles;
    }
}
