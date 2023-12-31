package com.vanannek.user.service;

import com.vanannek.user.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAll();
    void deleteAll();
    List<UserDTO> getUsersWithoutCurUser(String username);
    List<UserDTO> getUnchattedUsers(String curUsername);
    boolean exists(String username);
}
