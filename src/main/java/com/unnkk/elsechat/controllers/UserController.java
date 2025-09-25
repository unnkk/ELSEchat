package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.DTOs.PostResponseDTO;
import com.unnkk.elsechat.DTOs.UserDTO;
import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.exceptions.NotFoundException;
import com.unnkk.elsechat.services.PostService;
import com.unnkk.elsechat.services.UserService;
import com.unnkk.elsechat.utils.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts(@PathVariable long id) {
        UserDTO user = UserMapper.toDTO(userService.getUserById(id).orElseThrow(() -> new NotFoundException("User not found")));
        return ResponseEntity.ok(postService.getPostsByAuthor(user.id()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok(Map.of("status", Integer.toString(HttpServletResponse.SC_NO_CONTENT)));
    }
}
