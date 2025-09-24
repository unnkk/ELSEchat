package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.exceptions.IncorrectPasswordException;
import com.unnkk.elsechat.exceptions.NotFoundException;
import com.unnkk.elsechat.exceptions.UsernameAlreadyExistsException;
import com.unnkk.elsechat.services.UserService;
import com.unnkk.elsechat.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        String displayName = map.get("displayName");

        if(userService.getUserByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException(username);
        }
        userService.createUser(username, passwordEncoder.encode(password), displayName);

        return ResponseEntity.ok(Map.of("Status", "User registered successfully.",
                "Token", jwtUtil.genToken(username)));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        User user = userService.getUserByUsername(username).orElseThrow(() -> new NotFoundException(username));

        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new IncorrectPasswordException("Incorrect password.");
        }

        String token = jwtUtil.genToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
