package com.sknoor07.pos_system.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sknoor07.pos_system.modals.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserRole role;

    private LocalDateTime createdAt, updatedAt, lastLoginAt;
}
