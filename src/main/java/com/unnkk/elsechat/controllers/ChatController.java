package com.unnkk.elsechat.controllers;

import com.unnkk.elsechat.DTOs.ChatMessage;
import com.unnkk.elsechat.exceptions.UnprocessableEntityException;
import com.unnkk.elsechat.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final JWTUtil jwtUtil;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(@Header("Authorization") String token, @Payload ChatMessage inMessage) {
        if(token == null || !token.startsWith("Bearer ")) throw new UnprocessableEntityException("Token is required");
        token = token.substring(7);
        String username = jwtUtil.validateAndGetUsername(token);
        if(username == null) throw new UnprocessableEntityException("Invalid token");

        return new ChatMessage(username,
                inMessage.content(), OffsetDateTime.now());
    }
}
