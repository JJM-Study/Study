package com.jm.p_ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
              .authorizeRequests(authorize -> authorize
                      .requestMatchers("/h2-console/**").permitAll()
                      .requestMatchers("/chat-ws/**").permitAll() // WebSocket 엔드포인트 허용
                      //.anyRequest().permitAll() // 모든 요청 허용
                      .anyRequest().authenticated()
                      )
                      .formLogin(withDefaults()) // 기본 로그인 폼 사용 2024/08/13 추가
                      .cors(withDefaults()) // CORS 설정 활성화 20240812 추가
                      .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
                      ); // H2 콘솔 사용을 위한 frame 옵션 설정

      return http.build();

  }

  @Bean
  public InMemoryUserDetailsManager userDetailService(PasswordEncoder passwordEncoder) {
      UserDetails user = User.builder()
      .username("user")
      .password(passwordEncoder.encode("password"))
              .roles("USER")
              .build();

      return new InMemoryUserDetailsManager(user);
  }

  @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

}