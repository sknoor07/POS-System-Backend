package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.AuthService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user registration (signup) and login authentication (signin)")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "User Signup", description = "Registers a new user (Store Admin, Branch Manager, Cashier, etc.) in the POS system and returns a JWT authentication token.")
    @ApiResponse(responseCode = "201", description = "User successfully registered and JWT returned")
    @ApiResponse(responseCode = "400", description = "Bad Request / Email already exists")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDTO userDTO) throws UserException {
        AuthResponse response = authService.signUp(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "User Signin / Login", description = "Authenticates user credentials (email & password) and returns a valid JWT authentication token.")
    @ApiResponse(responseCode = "200", description = "Authentication successful and JWT returned")
    @ApiResponse(responseCode = "401", description = "Invalid credentials / Unauthorized")
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signinHandler(@RequestBody UserDTO userDTO) throws UserException {
        AuthResponse response = authService.signIn(userDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}

