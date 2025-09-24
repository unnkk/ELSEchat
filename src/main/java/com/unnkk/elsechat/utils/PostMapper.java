package com.unnkk.elsechat.utils;

import com.unnkk.elsechat.DTOs.PostResponseDTO;
import com.unnkk.elsechat.entities.Post;

public class PostMapper {
    public static PostResponseDTO toResponseDTO(Post post) {
        return new PostResponseDTO(post.getId(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getAuthor().getDisplayName(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
