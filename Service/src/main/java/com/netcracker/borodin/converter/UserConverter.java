package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.User;

public class UserConverter {

    public static UserDTO converter(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .state(user.getState().name())
                .role(user.getRole().name())
                .build();
    }
}
