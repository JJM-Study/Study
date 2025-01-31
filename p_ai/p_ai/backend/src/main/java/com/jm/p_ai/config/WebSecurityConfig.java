package com.jm.p_ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor;      // 2024/11/19 추가 // 계속 이어서 할 것.

//    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter, WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.webSocketAuthChannelInterceptor = webSocketAuthChannelInterceptor;
    } // 2024/11/19 수정


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
              .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 활성화 2024/11/14 추가
              .authorizeHttpRequests(authorize -> authorize
                      .requestMatchers("/api/authenticate").permitAll() // JWT 발급 엔드포인트는 인증 제외
                      .requestMatchers("/api/validate-token").permitAll() // 2025/01/26 유효성 검사 추가
                      .requestMatchers("/h2-console/**").permitAll()
                      .requestMatchers("/chat-ws/**").permitAll() // WebSocket 엔드포인트 허용
                      //.requestMatchers("/chat/**").permitAll() // SockJS 사용 시, /chat/Info 경로 허용이 필요하므로 일단 하위 경로 허용 2025/01/26
                      .requestMatchers("/loginForm").permitAll() // LoginForm 엔드포인트 허용
                      //.requestMatchers("/", "health", "/chat").permitAll() // /chat 엔드포인트 인증 없이 허용
                      .requestMatchers("/", "health", "/chat/**").permitAll() // /chat 엔드포인트 인증 없이 허용 (/chat/info 문제로 /chat 하위 허용 2025/01/26)
                      .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                      .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight 요청 허용
                      //.requestMatchers("/**").permitAll() // // 2025/01/26 수정.
                      .requestMatchers("/favicon.ico").permitAll()
                      //.anyRequest().permitAll() // 모든 요청 허용
                      .requestMatchers("/api/**").permitAll() // 2025/01/31 추가
                      .anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                      //.formLogin(withDefaults()) // 기본 로그인 폼 사용 2024/08/13 추가 / 2024/11/01 임시 주석 처리.
                      .cors(withDefaults()) // CORS 설정 활성화 20240812 추가
                      .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // H2 콘솔 사용을 위한 frame 옵션 설정
                      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();

  }

  // Jwt 자체에서 사용자 정보를 관리하므로 비활성화.
//  @Bean
//  public InMemoryUserDetailsManager userDetailService(PasswordEncoder passwordEncoder) {
//      UserDetails user = User.builder()
//      .username("user")
//      .password(passwordEncoder.encode("password"))
//              .roles("USER")
//              .build();
//
//      return new InMemoryUserDetailsManager(user);
//  }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //configuration.setAllowedOrigins(Arrays.asList("*")); // 모든 출처 허용
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000", "http://127.0.0.1:5000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true); // 브라우저가 쿠키, 인증 헤더 또는 TLS 클라이언트 인증서와 같은 자격 증명을 포함할 수 있도록.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        //configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080")); // 클라이언트 출처

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}