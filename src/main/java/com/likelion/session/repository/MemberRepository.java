package com.likelion.session.repository;

import com.likelion.session.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원 정보 가져오기
    Member findByEmail(String memberId);
}

