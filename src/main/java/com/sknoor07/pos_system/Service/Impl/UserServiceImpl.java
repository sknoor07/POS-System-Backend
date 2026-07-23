package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.repository.UserRepository;
import com.sknoor07.pos_system.security_configuration.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        if (token == null || !token.startsWith("Bearer ") || token.length() <= 7) {
            throw new UserException("Invalid User");
        }
        String email;
        try {
            email = jwtProvider.getEmailFromJwtToken(token);
        } catch (Exception e) {
            throw new UserException("Invalid User");
        }
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("Invalid User");
        }
        return user;
    }

    @Override
    public User getCurrentUser()     throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken || 
                "anonymousUser".equals(authentication.getName())) {
            throw new UserException("User not authenticated");
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserByuId(long id) throws UserException {
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
