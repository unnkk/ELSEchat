package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.DTOs.UserDTO;
import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.services.UserService;
import com.unnkk.elsechat.utils.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}
