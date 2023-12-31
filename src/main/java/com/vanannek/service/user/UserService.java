package com.vanannek.service.user;

import com.vanannek.dto.UserDTO;

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
