package com.vanannek.restcontroller;

import com.vanannek.exception.UserNotFoundException;
import com.vanannek.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private static final Logger log = LogManager.getLogger(LoginRestController.class);

    @Autowired private UserService userService;

    @PostMapping("/login-process")
    public ResponseEntity<Void> processLogin(@RequestParam String username,
                                             @RequestParam String plainPass,
                                             HttpSession session) {
        log.info("Process login, http session Id: " + session.getId());
        try {
            if (userService.login(username, plainPass)) {
                session.setAttribute("username", username);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> processLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                session.invalidate();
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
