package com.sknoor07.pos_system;

import com.sknoor07.pos_system.Service.Impl.UserServiceImpl;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.repository.UserRepository;
import com.sknoor07.pos_system.security_configuration.JwtProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getUserFromJwtToken_ValidToken_Success() throws UserException {
        String token = "Bearer valid.jwt.token";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(jwtProvider.getEmailFromJwtToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.getUserFromJwtToken(token);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void getUserFromJwtToken_NullToken_ThrowsException() {
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserFromJwtToken(null);
        });
        assertEquals("Invalid User", exception.getMessage());
    }

    @Test
    void getUserFromJwtToken_InvalidPrefix_ThrowsException() {
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserFromJwtToken("Basic invalid_prefix");
        });
        assertEquals("Invalid User", exception.getMessage());
    }

    @Test
    void getUserFromJwtToken_TooShort_ThrowsException() {
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserFromJwtToken("Bearer ");
        });
        assertEquals("Invalid User", exception.getMessage());
    }

    @Test
    void getUserFromJwtToken_ParsingException_ThrowsException() {
        String token = "Bearer invalid.token";
        when(jwtProvider.getEmailFromJwtToken(token)).thenThrow(new RuntimeException("Parsing failure"));

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserFromJwtToken(token);
        });
        assertEquals("Invalid User", exception.getMessage());
    }

    @Test
    void getUserFromJwtToken_UserNotFound_ThrowsException() {
        String token = "Bearer valid.jwt.token";
        String email = "notfound@example.com";

        when(jwtProvider.getEmailFromJwtToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserFromJwtToken(token);
        });
        assertEquals("Invalid User", exception.getMessage());
    }

    @Test
    void getCurrentUser_Authenticated_Success() throws UserException {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getCurrentUser();
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getCurrentUser_NullAuthentication_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });
        assertEquals("User not authenticated", exception.getMessage());
    }

    @Test
    void getCurrentUser_NotAuthenticated_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });
        assertEquals("User not authenticated", exception.getMessage());
    }

    @Test
    void getCurrentUser_AnonymousToken_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        AnonymousAuthenticationToken authentication = mock(AnonymousAuthenticationToken.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });
        assertEquals("User not authenticated", exception.getMessage());
    }

    @Test
    void getCurrentUser_AnonymousName_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("anonymousUser");

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });
        assertEquals("User not authenticated", exception.getMessage());
    }

    @Test
    void getCurrentUser_UserNotFound_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("notfound@example.com");

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });
        assertEquals("User not found", exception.getMessage());
    }
}
