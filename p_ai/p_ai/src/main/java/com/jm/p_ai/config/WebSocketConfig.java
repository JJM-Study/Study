package com.jm.p_ai.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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
                .setAllowedOrigins("http://localhost:3000", "http://localhost:8080")
                .addInterceptors(new JwtHandshakeInterceptor(jwtUtil)) // JWT 핸드셰이크 인터셉터 추가
                .withSockJS(); // 2022/11/14 수정
    }
}
