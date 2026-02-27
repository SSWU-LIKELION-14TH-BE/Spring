package com.likelion.session.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//로그인 응답 DTO
@Data
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {
    private String email;
    private String token;
}

