package com.vanannek.service.user;

import com.vanannek.exception.UserNotFoundException;
import com.vanannek.repos.UserRepos;
import com.vanannek.dto.UserDTO;
import com.vanannek.entity.User;
import com.vanannek.mapper.UserMapper;
import com.vanannek.util.PasswordEncoderUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        User user = userRepos.getReferenceById(username);
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
    public boolean login(String username, String plainPass) {
        User user = userRepos
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not found any users with username=" + username));
        String hashedPass = user.getPassHash();
        return PasswordEncoderUtils.comparePass(plainPass, hashedPass);
    }

    @Override
    public User.EGender getGenderByUsername(String username) {
        return userRepos.getGenderByUsername(username);
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
}
