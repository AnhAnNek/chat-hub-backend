package com.vanannek.user;

import com.vanannek.user.service.UserService;
import com.vanannek.websocket.OnlineUserStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Retrieve all",
            description = "Retrieve all users.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOs =  userService.getAll();
        log.info(UserUtils.RETRIEVED_ALL_USERS, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(
            summary = "Retrieve users without the current user",
            description = "Retrieve all users without the current user.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-all-without-cur-user")
    public ResponseEntity<List<UserDTO>> getUsersWithoutCurUser(@RequestParam("username") String username) {
        List<UserDTO> userDTOs = userService.getUsersWithoutCurUser(username);
        log.info(UserUtils.RETRIEVED_USERS_WITHOUT_CUR_USER, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(
            summary = "Retrieve unchatted users",
            description = "Retrieve all users who have not chatted with the current username.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-unchatted-users")
    public ResponseEntity<List<UserDTO>> showUnchattedUsers(@RequestParam("curUsername") String curUsername) {
        List<UserDTO> userDTOs = userService.getUnchattedUsers(curUsername);
        log.info(UserUtils.RETRIEVED_UNCHATTED_USERS, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(
            summary = "Retrieve online users",
            description = "Retrieve all users who are currently online.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-online-users")
    public ResponseEntity<List<String>> getOnlineUsers() {
        List<String> onlineUsernames = OnlineUserStore.getIns().getOnlineUsers();
        log.info(UserUtils.RETRIEVED_ONLINE_USERS, onlineUsernames.size());
        return ResponseEntity.ok(onlineUsernames);
    }
}
