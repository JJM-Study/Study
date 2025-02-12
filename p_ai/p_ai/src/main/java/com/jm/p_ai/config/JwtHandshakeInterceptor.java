package com.jm.p_ai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes) throws Exception {

        // 요청 URI에서 token 쿼리 파라미터 가져오기
        String query = request.getURI().getQuery();
        if (query != null && query.contains("token=")) {
            String token = query.substring(query.indexOf("token=") + 6);
            // 검증 Jwt 로직 추가 (예 : JwtTokenUtil.validateToken(token))
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                Principal userPrincipal = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); // 추가 2024/11/19
                //attributes.put("user", jwtUtil.getUsernameFromToken(token)); // 검증된 사용자 정보 저장
                attributes.put("userPrincipal", userPrincipal); // 검증된 사용자 정보 저장 / 2024/11/19 수정
                return true;
            }
        }
        return false; // 인증 실패 시 연결 차단
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 필요 시 핸드셰이크 이후 작업 로직 구현할 것.
    }

}
