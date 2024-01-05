package com.vanannek.user;

import com.vanannek.user.service.UserService;
import com.vanannek.websocket.OnlineUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOs =  userService.getAll();
        log.info(UserUtils.RETRIEVED_ALL_USERS, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/get-all-without-cur-user")
    public ResponseEntity<List<UserDTO>> getUsersWithoutCurUser(@RequestParam("username") String username) {
        List<UserDTO> userDTOs = userService.getUsersWithoutCurUser(username);
        log.info(UserUtils.RETRIEVED_USERS_WITHOUT_CUR_USER, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/get-unchatted-users")
    public ResponseEntity<List<UserDTO>> showUnchattedUsers(@RequestParam("curUsername") String curUsername) {
        List<UserDTO> userDTOs = userService.getUnchattedUsers(curUsername);
        log.info(UserUtils.RETRIEVED_UNCHATTED_USERS, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/get-online-users")
    public ResponseEntity<List<String>> getOnlineUsers() {
        List<String> onlineUsernames = OnlineUserStore.getIns().getOnlineUsers();
        log.info(UserUtils.RETRIEVED_ONLINE_USERS, onlineUsernames.size());
        return ResponseEntity.ok(onlineUsernames);
    }
}
