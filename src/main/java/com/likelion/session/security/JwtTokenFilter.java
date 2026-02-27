package com.likelion.session.security;

import com.likelion.session.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// 로그인 이후 모든 요청을 가로채 토큰 유무와 진위 파악 의뢰 및 인증 도장
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 헤더에서 토큰 꺼내기
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 토큰 존재 && 토큰이 진짜라면 (Provider에게 의뢰)
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 토큰에서 이메일 꺼내기
            String email = jwtTokenProvider.getEmail(token);

            // 4. 아까 만든 db 확인 메서드로 유저 정보를 가져오기
            UserDetails userDetails = userService.loadUserByUsername(email);

            // 5. 시큐리티가 인증 됐음을 믿을 수 있도록 인증 도장을 제작
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. 시큐리티 세션에 인증 도장 찍기 -> 인증된 요청으로 바뀜
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 7. 목적지로 보내기
        filterChain.doFilter(request, response);
    }
}
