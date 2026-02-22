package com.example.project.paging_transaction.service;

import com.example.project.paging_transaction.entity.Member;
import com.example.project.paging_transaction.entity.Post;
import com.example.project.paging_transaction.repository.MemberRepository;
import com.example.project.paging_transaction.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void saveWithTransaction(Long memberId) {
        postRepository.save(Post.builder().title("트랜잭션O").content("저장").build());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버 없음"));
        member.setPoint(member.getPoint() + 10);
        memberRepository.save(member);
        throw new RuntimeException("실패 발생"); // 여기서 예외
    }
}
