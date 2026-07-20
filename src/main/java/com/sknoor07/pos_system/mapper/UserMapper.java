package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.User;
import com.sknoor07.pos_system.payload.dto.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User savedUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setFullName(savedUser.getFullName());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setPhoneNumber(savedUser.getPhoneNumber());
        userDTO.setRole(savedUser.getRole());
        userDTO.setCreatedAt(savedUser.getCreatedAt());
        userDTO.setLastLoginAt(savedUser.getLastLoginAt());
        userDTO.setUpdatedAt(savedUser.getUpdatedAt());
        return userDTO;
    }
}
