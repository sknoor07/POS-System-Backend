package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.AuthService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.AuthResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResposne> signupHandler(@RequestBody UserDTO userDTO) throws UserException {
        AuthResposne response = authService.SignUp(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResposne> signinHandler(@RequestBody UserDTO userDTO) throws UserException {
        AuthResposne response = authService.SignIn(userDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }
}

