package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.UserMapper;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Profile Management", description = "Endpoints for retrieving user profile and user details by ID")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get Authenticated User Profile", description = "Retrieves the user profile and role of the currently authenticated user from their JWT Bearer token.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile")
    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid JWT token")
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String jwtToken) throws UserException {
        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(user));
    }

    @Operation(summary = "Get User Profile by ID", description = "Retrieves user details by user ID (restricted to self, Store Admin, Store Manager, or Branch Manager).")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user details")
    @ApiResponse(responseCode = "403", description = "Access denied: Insufficient permissions")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "Bearer JWT token", required = true) @RequestHeader("Authorization") String jwtToken,
            @Parameter(description = "ID of the target user", required = true) @PathVariable Long id) throws UserException {
        User requester = userService.getUserFromJwtToken(jwtToken);
        if (requester == null || (!requester.getId().equals(id) &&
                requester.getRole() != UserRole.ROLE_ADMIN &&
                requester.getRole() != UserRole.ROLE_STORE_MANAGER &&
                requester.getRole() != UserRole.ROLE_BRANCH_MANAGER)) {
            throw new UserException("Access denied: You do not have permission to access this profile.");
        }
        User user = userService.getUserByuId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(user));
    }
}
