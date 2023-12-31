package com.vanannek.restcontroller;

import com.vanannek.dto.UserDTO;
import com.vanannek.service.user.UserService;
import com.vanannek.websocket.OnlineUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOs =  userService.getAll();
        return userDTOs;
    }

    @GetMapping("/get-all-without-cur-user")
    public List<UserDTO> getUsersWithoutCurUser(@RequestParam("username") String username) {
        List<UserDTO> userDTOs = userService.getUsersWithoutCurUser(username);
        return userDTOs;
    }

    @GetMapping("/get-unchatted-users")
    public List<UserDTO> showUnchattedUsers(@RequestParam("curUsername") String curUsername) {
        List<UserDTO> userDTOs = userService.getUnchattedUsers(curUsername);
        return userDTOs;
    }

    @GetMapping("/get-online-users")
    public List<String> getOnlineUsers() {
        return OnlineUserStore.getIns().getOnlineUsers();
    }
}
