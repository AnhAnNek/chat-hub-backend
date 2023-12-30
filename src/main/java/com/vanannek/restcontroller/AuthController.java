package com.vanannek.restcontroller;

import com.vanannek.dto.UserDTO;
import com.vanannek.request.LoginRequest;
import com.vanannek.request.RegisterRequest;
import com.vanannek.response.AuthResponse;
import com.vanannek.security.JwtGenerator;
import com.vanannek.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.vanannek.security.SecurityConstants.DEFAULT_TOKEN_TYPE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LogManager.getLogger(AuthController.class);

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.username();
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            loginRequest.plainPass()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            Date exp = jwtGenerator.getExpiration(token);
            AuthResponse authResponse = new AuthResponse(username, token, DEFAULT_TOKEN_TYPE, exp);
            log.info("`{}` login success", username);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.getUserByUsername(registerRequest.username()) != null) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        String username = registerRequest.username();
        String hashedPass = passwordEncoder.encode(registerRequest.plainPass());
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .passHash(hashedPass)
                .fullName(registerRequest.fullName())
                .email(registerRequest.email())
                .gender(registerRequest.gender())
                .build();

        userService.save(userDTO);
        log.info("`{}` register success", username);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
