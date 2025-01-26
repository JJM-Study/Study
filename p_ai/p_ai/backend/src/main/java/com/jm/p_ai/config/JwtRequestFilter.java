package com.jm.p_ai.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

            final String authorizationHeader = request.getHeader("Authorization");

            // --- 2025/01/06 JWT 필터 예외 경우를 위해 추가함. ---
            final String requestPath = request.getRequestURI();

            //if (requestPath.equals("/") || requestPath.equals("health")) { // 현재 "/" 경로만 테스트를 위해 제외
            //if (requestPath.equals("/") || requestPath.equals("health") || requestPath.equals("/api/authenticate") || requestPath.equals("/api/validate-token") || requestPath.startsWith("/chat/")) { // 현재 "/" 경로만 테스트를 위해 제외
            if (requestPath.equals("/") || requestPath.equals("health") || requestPath.equals("/api/authenticate") || requestPath.equals("/api/validate-token")) { // 현재 "/" 경로만 테스트를 위해 제외
                System.out.println("Skipping JWT filter for path: " + requestPath);
                chain.doFilter(request, response);
                return;
            }
            // --------------------------------------------

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.getUsernameFromToken(jwt);
            }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.isTokenValid(jwt)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            chain.doFilter(request,response);

        }
    }