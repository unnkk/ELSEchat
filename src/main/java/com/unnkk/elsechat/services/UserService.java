package com.unnkk.elsechat.services;

import com.unnkk.elsechat.DTOs.UserDTO;
import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.repositories.PostRepository;
import com.unnkk.elsechat.repositories.UserRepository;
import com.unnkk.elsechat.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public User createUser(String username, String passwordHash, String displayName){
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setDisplayName(displayName);
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserDTO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        User currentUser = getUserByUsername(authentication.getName())
                .orElseThrow(() -> new InternalError("Failed to get user id"));
        return UserMapper.toDTO(currentUser);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    public void deleteCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new InternalError("Failed to get user id"));

        postRepository.deleteAll(postRepository.findByAuthorId(currentUser.getId()));
        userRepository.delete(currentUser);
    }
}
