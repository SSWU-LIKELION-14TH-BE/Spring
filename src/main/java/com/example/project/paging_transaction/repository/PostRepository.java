package com.example.project.paging_transaction.repository;

import com.example.project.paging_transaction.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// post 레포지토리 만들기
public interface PostRepository extends JpaRepository<Post, Long> {
    // 페이징 기능은 JpaRepository에서 기본 제공
}
