package com.unnkk.elsechat.DTOs;

import com.unnkk.elsechat.entities.User;

public record UserDTO(Long id, String username, String displayName) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getDisplayName());
    }
}
