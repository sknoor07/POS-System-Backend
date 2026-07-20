package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signUp(UserDTO user) throws UserException;
    AuthResponse signIn(UserDTO user) throws UserException;


}
