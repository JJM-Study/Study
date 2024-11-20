package com.jm.p_ai.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            // 핸드셰이크 시 저장했던 사용자 정보를 가져와 SecurityContext에 설정
            Principal userPrincipal = (Principal) accessor.getSessionAttributes().get("userPrincipal");
            if (userPrincipal != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal.getName(), null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication); // Principal 설정 추가
            }
        }

        return message;
    }
}

