package com.vanannek.restcontroller;

import com.vanannek.dto.UserDTO;
import com.vanannek.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-without-cur-user")
    public List<UserDTO> getUsersWithoutCurUser(@RequestParam("username") String username) {
        List<UserDTO> users = userService.getUsersWithoutCurUser(username);
        return users;
    }
}
