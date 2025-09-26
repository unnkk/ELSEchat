package com.unnkk.elsechat.configuration;

import com.unnkk.elsechat.exceptions.UnprocessableEntityException;
import com.unnkk.elsechat.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JWTUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(Objects.equals(accessor.getCommand(), StompCommand.CONNECT)) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtUtil.validateAndGetUsername(token);
                if(username != null) {
                    System.out.println("Connection successful for " + username);
                    return message;
                }
            }
            throw new UnprocessableEntityException("Invalid JWT Token");
        }
        return message;
    }
}
