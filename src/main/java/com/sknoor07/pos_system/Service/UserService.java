package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;

import java.util.List;

public interface UserService {
    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserByuId(long id) throws UserException;
    List<User> getAllUsers();
}
