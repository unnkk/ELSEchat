package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.repositories.UserRepository;
import com.unnkk.elsechat.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        String displayName = map.get("displayName");

        if(userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("Status", "User already exists"));
        }
        User user =  new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setDisplayName(displayName);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("Status", "User registered successfully.",
                "Token", jwtUtil.genToken(username)));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.genToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
