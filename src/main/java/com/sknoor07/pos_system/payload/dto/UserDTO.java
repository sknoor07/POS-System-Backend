package com.sknoor07.pos_system.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sknoor07.pos_system.modals.userRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;

    private userRole role;

    private LocalDateTime createdAt, updatedAt, lastLoginAt;;
}
