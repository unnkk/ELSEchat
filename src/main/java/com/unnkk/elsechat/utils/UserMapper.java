package com.unnkk.elsechat.utils;

import com.unnkk.elsechat.DTOs.UserDTO;
import com.unnkk.elsechat.entities.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getDisplayName());
    }
}
