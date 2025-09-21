package com.unnkk.elsechat.services;

import com.unnkk.elsechat.entities.User;
import com.unnkk.elsechat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
