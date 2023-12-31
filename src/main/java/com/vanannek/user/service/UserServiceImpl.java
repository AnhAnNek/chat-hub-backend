package com.vanannek.user.service;

import com.vanannek.user.UserNotFoundException;
import com.vanannek.user.UserRepos;
import com.vanannek.user.UserDTO;
import com.vanannek.user.User;
import com.vanannek.user.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired private UserRepos userRepos;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any users with username:" + username));
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepos.findAll();
        return userMapper.toDTOs(users);
    }

    @Override
    public void deleteAll() {
        userRepos.deleteAll();
    }

    @Override
    public List<UserDTO> getUsersWithoutCurUser(String username) {
        List<UserDTO> users = getAll();
        users.removeIf(u -> u.getUsername().equals(username));
        return users;
    }

    @Override
    public List<UserDTO> getUnchattedUsers(String curUsername) {
        List<User> users = userRepos.getUnchattedUsers(curUsername);
        return userMapper.toDTOs(users);
    }

    @Override
    public boolean exists(String username) {
        return userRepos.existsById(username);
    }
}
