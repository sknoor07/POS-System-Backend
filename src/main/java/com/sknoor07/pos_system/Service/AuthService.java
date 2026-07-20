package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.AuthResposne;

public interface AuthService {

    AuthResposne SignUp(UserDTO user) throws UserException;
    AuthResposne SignIn(UserDTO user) throws UserException;


}
