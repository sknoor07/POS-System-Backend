package com.sknoor07.pos_system.mapper;

import com.sknoor07.pos_system.modals.user.User;
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
        userDTO.setBranchId(savedUser.getBranch()!=null?savedUser.getBranch().getId():null);
        userDTO.setStoreId(savedUser.getStore()!=null?savedUser.getStore().getId():null);
        userDTO.setLastLoginAt(savedUser.getLastLoginAt());
        userDTO.setUpdatedAt(savedUser.getUpdatedAt());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        return  User.builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .lastLoginAt(userDTO.getLastLoginAt())
                .updatedAt(userDTO.getUpdatedAt())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .build();
    }
}
