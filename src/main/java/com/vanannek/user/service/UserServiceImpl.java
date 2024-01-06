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

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired private UserRepos userRepos;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any users with username:" + username));
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
