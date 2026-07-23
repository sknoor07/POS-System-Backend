package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.UserMapper;
import com.sknoor07.pos_system.modals.User;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwtToken) throws UserException {
        User user=userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) throws UserException {
        User user=userService.getUserByuId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(user));
    }


}
