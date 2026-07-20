package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.AuthService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.mapper.UserMapper;

import com.sknoor07.pos_system.modals.User;
import com.sknoor07.pos_system.modals.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.AuthResponse;
import com.sknoor07.pos_system.repository.UserRepository;
import com.sknoor07.pos_system.security_configuration.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserimplementation customUserimplementation;

    @Override
    public AuthResponse signUp(UserDTO userDto) throws UserException {
        User user= userRepository.findByEmail(userDto.getEmail());
        if(user!=null){
            throw new UserException(" Email id is already registered...");
        }
        if (UserRole.ROLE_ADMIN.equals(userDto.getRole())){
            throw new UserException(" Role admin is not allowed...");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User newUser= new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setLastLoginAt(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        User SavedUser=userRepository.save(newUser);

        GrantedAuthority authority = new SimpleGrantedAuthority(SavedUser.getRole().name());

        List<GrantedAuthority> authorities = List.of(authority);


        Authentication authentication = new UsernamePasswordAuthenticationToken(SavedUser.getEmail(),null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUser(UserMapper.toDTO(SavedUser));

        return authResponse;
    }

    @Override
    public AuthResponse signIn(UserDTO userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        User user= userRepository.findByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("LoggedIn Successfully");
        authResponse.setUser(UserMapper.toDTO(user));

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserimplementation.loadUserByUsername(email);

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("password does not match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
