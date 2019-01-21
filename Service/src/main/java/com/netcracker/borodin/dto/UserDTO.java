package com.netcracker.borodin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;
    private String email;
    private long id;
    private String name;
    private String state;
    private String role;
    private String surname;
}
