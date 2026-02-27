package com.likelion.session.config;

import com.likelion.session.security.JwtTokenFilter;
import com.likelion.session.security.JwtTokenProvider;
import com.likelion.session.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 쿠키 안쓰니까 CSRF 끄기

                // 세션 사용 안함 명시
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // 회원가입과 로그인은 토큰이 없어도 접근 가능
                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll()
                        // 나머지는 모두 인증 필요
                        .anyRequest().authenticated()
                )

                // 모든 요청에 JWT 필터를 적용되게 만듦
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, userService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
