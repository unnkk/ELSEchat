package com.unnkk.elsechat.DTOs;

import java.time.OffsetDateTime;

public record PostResponseDTO(
        long id,
        String content,
        long author_id,
        String author_name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
