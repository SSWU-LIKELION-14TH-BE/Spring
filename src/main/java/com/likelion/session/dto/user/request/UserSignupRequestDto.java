package com.likelion.session.dto.user.request;

import lombok.Data;

//회원가입 요청 DTO
@Data
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String profileImage;
}

