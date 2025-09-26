package com.unnkk.elsechat.DTOs;

import java.time.OffsetDateTime;

public record ChatMessage(String sender, String content, OffsetDateTime timestamp) {}
