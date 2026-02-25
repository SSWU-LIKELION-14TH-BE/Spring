package com.likelion.session.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    // 필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment 표시, 식별자 표시
    @Column(name = "menber_id", unique = true, nullable = false) // name 명시하면 db 칼럼 이름 지정 가능, 미지정 시 필드명으로
    private Long id;

    @Column(length = 15, nullable = false) // length로 최대 글자 수 지정 가능, null 가능 여부 지정 가능
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String contact;
}
