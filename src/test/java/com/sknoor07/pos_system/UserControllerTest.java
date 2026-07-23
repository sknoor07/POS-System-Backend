package com.sknoor07.pos_system;

import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.controller.UserController;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sknoor07.pos_system.payload.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_Owner_Success() throws UserException {
        String token = "Bearer token";
        Long userId = 1L;

        User requester = new User();
        requester.setId(userId);
        requester.setRole(UserRole.ROLE_USER);
        requester.setEmail("user@example.com");
        requester.setFullName("User Name");

        when(userService.getUserFromJwtToken(token)).thenReturn(requester);
        when(userService.getUserByuId(userId)).thenReturn(requester);

        ResponseEntity<UserDTO> response = userController.getUserById(token, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User Name", response.getBody().getFullName());
        verify(userService, times(1)).getUserFromJwtToken(token);
        verify(userService, times(1)).getUserByuId(userId);
    }

    @Test
    void getUserById_Admin_Success() throws UserException {
        String token = "Bearer token";
        Long userId = 1L;

        User requester = new User();
        requester.setId(2L);
        requester.setRole(UserRole.ROLE_ADMIN);

        User targetUser = new User();
        targetUser.setId(userId);
        targetUser.setFullName("Target User");

        when(userService.getUserFromJwtToken(token)).thenReturn(requester);
        when(userService.getUserByuId(userId)).thenReturn(targetUser);

        ResponseEntity<UserDTO> response = userController.getUserById(token, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Target User", response.getBody().getFullName());
    }

    @Test
    void getUserById_StoreManager_Success() throws UserException {
        String token = "Bearer token";
        Long userId = 1L;

        User requester = new User();
        requester.setId(2L);
        requester.setRole(UserRole.ROLE_STORE_MANAGER);

        User targetUser = new User();
        targetUser.setId(userId);
        targetUser.setFullName("Target User");

        when(userService.getUserFromJwtToken(token)).thenReturn(requester);
        when(userService.getUserByuId(userId)).thenReturn(targetUser);

        ResponseEntity<UserDTO> response = userController.getUserById(token, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Target User", response.getBody().getFullName());
    }

    @Test
    void getUserById_NonOwnerNonPrivileged_ThrowsException() throws UserException {
        String token = "Bearer token";
        Long userId = 1L;

        User requester = new User();
        requester.setId(2L);
        requester.setRole(UserRole.ROLE_USER);

        when(userService.getUserFromJwtToken(token)).thenReturn(requester);

        assertThrows(UserException.class, () -> {
            userController.getUserById(token, userId);
        });

        verify(userService, never()).getUserByuId(anyLong());
    }
}
