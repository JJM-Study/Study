package com.jm.p_ai.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public JwtUtil jwtUtil;
    public WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor;

    public WebSocketConfig(JwtUtil jwtUtil, WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor) {
        this.jwtUtil = jwtUtil;
        this.webSocketAuthChannelInterceptor = webSocketAuthChannelInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //registry.addEndpoint("/chat-ws").setAllowedOriginPatterns("*").withSockJS();
        //registry.addEndpoint("/chat-ws").setAllowedOrigins("*").withSockJS(); // 2022/11/14 수정
        registry.addEndpoint("/chat-ws")
                //.setAllowedOrigins("http://localhost:3000", "http://localhost:8080")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:8080", "http://d2y1bi4w3u4drt.cloudfront.net", "https://d2y1bi4w3u4drt.cloudfront.net", "https://web-pj.com", "https://www.web-pj.com") // CloudFront 등, 배포를 위한 설정.
                .addInterceptors(new JwtHandshakeInterceptor(jwtUtil)) // JWT 핸드셰이크 인터셉터 추가
                .withSockJS(); // 2022/11/14 수정
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthChannelInterceptor);
    }

}

