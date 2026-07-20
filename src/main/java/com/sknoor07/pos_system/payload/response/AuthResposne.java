package com.sknoor07.pos_system.payload.response;

import com.sknoor07.pos_system.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResposne {
    private String jwt;
    private String message;
    private UserDTO user;
}
