package com.jm.p_ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(Customizer.withDefaults()) // CSRF 보호 비활성화
              .authorizeRequests(authorize -> authorize
                      .anyRequest().permitAll() // 모든 요청 허용
              );
      return http.build();


      // 이하 주석 부분은 로그인 기능이 구현되었을 때 적용할 것.

//              .authorizeHttpRequests((authz) -> authz
//                      .requestMatchers("/public/**").permitAll()  // 예전의 antMatchers 대신 requestMatchers 사용
//                      .anyRequest().authenticated()
//              )

//              .formLogin((form) -> form
//                      .loginPage("/login").permitAll()
//              )
//              .logout((logout) -> logout.permitAll())
//

  }

}
