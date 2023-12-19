package com.vanannek.service.user;

import com.vanannek.dto.UserDTO;
import com.vanannek.entity.User;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAll();

    void deleteAll();

    boolean login(String username, String plainPass);

    User.EGender getGenderByUsername(String username);

    List<UserDTO> getUsersWithoutCurUser(String username);

    List<UserDTO> getUnchattedUsers(String curUsername);
}
